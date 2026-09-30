package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Constants;

public class Localization {
    private GoBildaPinpointDriver pinpoint;
    private Limelight3A limelight;
    private LLResult result;
    private static Pose2D pose;
    private static Pose3D mt1Pose;
    private static Pose3D mt2Pose;

    private static double latestRotation;

    public Localization () {
//        pinpoint = opMode.hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(Constants.PinpointConstants.kEncoderResolution, DistanceUnit.MM);
        pinpoint.setOffsets(Constants.PinpointConstants.kPodXOffset,
                Constants.PinpointConstants.kPodYOffset,
                DistanceUnit.CM);

        pinpoint.setEncoderDirections(Constants.PinpointConstants.kPodXDirection,
                Constants.PinpointConstants.kPodYDirection);

        pinpoint.initialize();

        pinpoint.setPosX(12, DistanceUnit.INCH);
        pinpoint.setPosY(12, DistanceUnit.INCH);
        pinpoint.setHeading(90, AngleUnit.DEGREES);

//        limelight = opMode.hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(60);
        limelight.pipelineSwitch(1); // placeholder
        result = limelight.getLatestResult();


        latestRotation = getPoseRotation();
    }

    public void operate(MecanumDriveSubsystem mecanum) {
        pinpoint.update();
        limelight.updateRobotOrientation(getPoseRotation());
        result = limelight.getLatestResult();

        mt1Pose = result.getBotpose();
        mt2Pose = result.getBotpose_MT2();

        if (result != null && result.isValid() && isPoseSafe(mt2Pose)) {
            Pose2D newPose = new Pose2D(DistanceUnit.INCH,
                    mt2Pose.getPosition().x,
                    mt2Pose.getPosition().y,
                    AngleUnit.DEGREES,
                    mt2Pose.getOrientation().getYaw(AngleUnit.DEGREES));
            pose = newPose;
            pinpoint.setPosition(pose);
        }

        latestRotation = getPoseRotation();
    }

    // PINPOINT METHODS

    public Pose2D getRobotPose() {
        return pinpoint.getPosition();
    }
    public static double getPoseX() {
        return pose.getX(DistanceUnit.INCH);
    }
    public static double getPoseY() {
        return pose.getY(DistanceUnit.INCH);
    }
    public static double getPoseRotation() {
        return pose.getHeading(AngleUnit.DEGREES);
    }
    public double getRotationRadians() {
        return pinpoint.getHeading(AngleUnit.RADIANS);
    }

    public void setPose2d(double x, double y) {
        pose = new Pose2D(DistanceUnit.INCH, x, y, AngleUnit.DEGREES, getPoseRotation());
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
        if ((getPoseRotation() - latestRotation) / 0.02 > 360) {
            return false;
        }
        else if (pinpoint.getVelX(DistanceUnit.INCH) > 40) {
            return false;
        }
        else if (pose.getOrientation().getRoll(AngleUnit.DEGREES) != 0  &&
        pose.getOrientation().getRoll(AngleUnit.DEGREES) != 0) {
            return false;
        }
        else if (pose.getPosition().z != 0) {
            return false;
        }
        return true;
    }

}
