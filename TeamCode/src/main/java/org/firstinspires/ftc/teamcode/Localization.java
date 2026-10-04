package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

public class Localization {
    private GoBildaPinpointDriver pinpoint;
    private Limelight3A limelight;
    private LLResult result;
    private static Pose2D estimatedPose;
    private static Pose3D mt1Pose;
    private static Pose3D mt2Pose;
    private static Pose2D predictedPose;
    private static Pose2D initialPose;
    private PoseEKF ekf;

    private ElapsedTime elapsedTime;




    public Localization (OpMode opMode, Pose2D startingPose) {


        pinpoint = opMode.hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(Constants.PinpointConstants.kEncoderResolution, DistanceUnit.MM);
        pinpoint.setOffsets(Constants.PinpointConstants.kPodXOffset,
                Constants.PinpointConstants.kPodYOffset,
                DistanceUnit.CM);

        pinpoint.setEncoderDirections(Constants.PinpointConstants.kPodXDirection,
                Constants.PinpointConstants.kPodYDirection);

        limelight = opMode.hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(60);
        limelight.pipelineSwitch(1); // placeholder
        result = limelight.getLatestResult();

        pinpoint.initialize();

        initialPose = startingPose;
        ekf = new PoseEKF(initialPose);
        pinpoint.setPosition(initialPose);


        elapsedTime.startTime();
    }

    public void operate() {
        double dt = elapsedTime.seconds();
        elapsedTime.reset();

        pinpoint.update();

        ekf.predict(pinpoint.getVelX(DistanceUnit.INCH),
                pinpoint.getVelY(DistanceUnit.INCH),
                pinpoint.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES),
                dt);

        limelight.updateRobotOrientation(getPoseRotation());
        result = limelight.getLatestResult();

        estimatedPose = pinpoint.getPosition();

        if (result != null && result.isValid()) {
            mt1Pose = result.getBotpose();
            mt2Pose = result.getBotpose_MT2();
            if (mt2Pose != null && isPoseSafe(mt2Pose)) {

                double visionX = mt2Pose.getPosition().x;
                double visionY = mt2Pose.getPosition().y;
                double visionHeading = mt2Pose.getOrientation()
                        .getYaw(AngleUnit.DEGREES);

                ekf.update(visionX, visionY, visionHeading);
            }
        }



    }

    // PINPOINT METHODS
    public void resetRotation(double degrees) {
        estimatedPose = new Pose2D(DistanceUnit.INCH,
                estimatedPose.getX(DistanceUnit.INCH),
                estimatedPose.getY(DistanceUnit.INCH),
                AngleUnit.DEGREES,
                degrees);
        pinpoint.setHeading(degrees, AngleUnit.DEGREES);
    }
    public static Pose2D getRobotPose() {
        return estimatedPose;
    }
    public static double getPoseX() {
        return estimatedPose.getX(DistanceUnit.INCH);
    }
    public static double getPoseY() {
        return estimatedPose.getY(DistanceUnit.INCH);
    }
    public static double getPoseRotation() {
        return estimatedPose.getHeading(AngleUnit.DEGREES);
    }
    public double getRotationRadians() {
        return pinpoint.getHeading(AngleUnit.RADIANS);
    }

    public void setPose2d(double x, double y) {
        estimatedPose = new Pose2D(DistanceUnit.INCH, x, y, AngleUnit.DEGREES, getPoseRotation());
        pinpoint.setPosX(x, DistanceUnit.INCH);
        pinpoint.setPosY(y, DistanceUnit.INCH);
    }

    public static double getDistance(double targetX, double targetY) {
        return Math.sqrt(
                Math.pow(Math.abs(targetX - getPoseX()), 2)
                        + Math.pow(Math.abs(targetY - getPoseY()), 2)
        );
    }

    public static double getTargetRotation(double targetX, double targetY) {
        double changeX = targetX - getPoseX();
        double changeY = targetY - getPoseY();

        if (changeX > 0) {
            return Math.toDegrees(Math.atan2(changeY, changeX));
        }
        if (changeY > 0) {
            return Math.toDegrees(Math.atan2(changeY, changeX)) + 180;
        }
        return Math.toDegrees(Math.atan2(changeY, changeX)) - 180;
    }

    // LIMELIGHT METHODS

    public Pose3D getMT1() {
        return result.getBotpose();
    }
    public Pose3D getMT2() {
        return result.getBotpose_MT2();
    }
    public boolean isPoseSafe(Pose3D pose) {

        return !(pinpoint.getVelX(DistanceUnit.INCH) > 40)
                || !(pinpoint.getVelY(DistanceUnit.INCH) > 40)
                || !(pinpoint.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES) > 360)
                || !(pose.getOrientation().getRoll(AngleUnit.DEGREES) < 1  ||
                pose.getOrientation().getPitch(AngleUnit.DEGREES) < 1)
                ||(pose.getPosition().z > 0.5);
    }

    public double getRPMp (double x) { // pose
        double a = Constants.PinpointConstants.kPoseA;
        double b = Constants.PinpointConstants.kPoseB;
        double c = Constants.PinpointConstants.kPoseC;
        // 67
        return a * Math.pow(x, 2) + b * x + c;
    }

    public double getRPMl (double x) { // based on limelight tA
        double a = Constants.PinpointConstants.kLimeA;
        double b = Constants.PinpointConstants.kLimeB;
        double c = Constants.PinpointConstants.kLimeC;

        return a * Math.pow(x, 2) + b * x + c;
    }

    public Pose2D predictedPose(Pose2D currentPose, double dt) {
        double predictedX = currentPose.getX(DistanceUnit.INCH);
        double predictedY = currentPose.getY(DistanceUnit.INCH);
        double predictedTheta = currentPose.getHeading(AngleUnit.DEGREES);

        predictedX += pinpoint.getVelX(DistanceUnit.INCH) * dt;
        predictedY += pinpoint.getVelY(DistanceUnit.INCH) * dt;
        predictedTheta += pinpoint.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES) * dt;

        return new Pose2D(DistanceUnit.INCH, predictedX, predictedY, AngleUnit.DEGREES, predictedTheta);
    }

}
