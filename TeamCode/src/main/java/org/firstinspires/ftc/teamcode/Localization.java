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
    private LimelightLocalizer limelight;
    private PinpointLocalizer pinpoint;


    private LLResult result;
    private static Pose2D estimatedPose;
    private static Pose3D mt1Pose;
    private static Pose3D mt2Pose;
    private static Pose2D predictedPose;
    private static Pose2D initialPose;
    private PoseEKF ekf;

    public Localization (PinpointLocalizer pinpoint, LimelightLocalizer limelight, OpMode opMode, Pose2D startingPose) {
        this.pinpoint = pinpoint;
        this.limelight = limelight;

        initialPose = startingPose;
        ekf = new PoseEKF(initialPose);
        pinpoint.setPose(initialPose);
    }

    public void operate(double dt) {
        pinpoint.update();

        ekf.predict(pinpoint.getFieldXVelocity(),
                pinpoint.getFieldYVelocity(),
                pinpoint.getHeadingVelocity(), dt);

        limelight.update(pinpoint);
        result = limelight.getLatestResult();

        estimatedPose = pinpoint.getPose();

        if (result != null && result.isValid()) {
            mt1Pose = result.getBotpose();
            mt2Pose = result.getBotpose_MT2();
            if (mt2Pose != null && isPoseSafe(mt2Pose) && limelight.getTagCount() >= 2) {

                double visionX = mt2Pose.getPosition().x;
                double visionY = mt2Pose.getPosition().y;
                double visionHeading = mt2Pose.getOrientation()
                        .getYaw(AngleUnit.DEGREES);

                ekf.update(visionX, visionY, visionHeading);
                estimatedPose = ekf.getPose();
            }
        }



    }
    public static double getAngleError(double target, double current) {
        double error = (target - current) % 360.0;

        if (error > 180.0) {
            error -= 360.0;
        } else if (error < -180.0) {
            error += 360.0;
        }

        return error;
    }

    // PINPOINT METHODS
    public void resetRotation(double degrees) {
        estimatedPose = new Pose2D(DistanceUnit.INCH,
                estimatedPose.getX(DistanceUnit.INCH),
                estimatedPose.getY(DistanceUnit.INCH),
                AngleUnit.DEGREES,
                degrees);
        pinpoint.setHeading(degrees);
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
        return pinpoint.getHeading();
    }

    public void setPose2d(double x, double y) {
        estimatedPose = new Pose2D(DistanceUnit.INCH, x, y, AngleUnit.DEGREES, getPoseRotation());
        pinpoint.setPosX(x);
        pinpoint.setPosY(y);
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
            return Math.toDegrees(Math.atan2(changeY, changeX)) +   180;
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
        if (pose == null) {
            return false;
        }

        if (Math.abs(pinpoint.getVelocityX()) > 40 ||
                Math.abs(pinpoint.getVelocityY()) > 40 ||
                Math.abs(pinpoint.getHeadingVelocity()) > 360) {
            return false;
        }

        double roll = pose.getOrientation()
                .getRoll(AngleUnit.DEGREES);

        double pitch = pose.getOrientation()
                .getPitch(AngleUnit.DEGREES);

        double z = pose.getPosition().z;

        if (Math.abs(roll) > 15 ||
                Math.abs(pitch) > 15 ||
                Math.abs(z) > 6) {
            return false;
        }

        return true;
    }


    public Pose2D predictedPose(Pose2D currentPose, double dt) {
        double predictedX = currentPose.getX(DistanceUnit.INCH);
        double predictedY = currentPose.getY(DistanceUnit.INCH);
        double predictedTheta = currentPose.getHeading(AngleUnit.DEGREES);

        predictedX += pinpoint.getFieldXVelocity() * dt;
        predictedY += pinpoint.getFieldYVelocity() * dt;
        predictedTheta += pinpoint.getHeadingVelocity() * dt;

        return new Pose2D(DistanceUnit.INCH, predictedX, predictedY, AngleUnit.DEGREES, predictedTheta);
    }

}
