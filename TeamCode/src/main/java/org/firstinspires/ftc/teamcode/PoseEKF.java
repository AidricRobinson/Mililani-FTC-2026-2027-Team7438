package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class PoseEKF {

    private double x;
    private double y;
    private double heading;

    // Covariance
    private double pX;
    private double pY;
    private double pHeading;

    // How much uncertainty odometry adds per second
    private double qX = 0.5;
    private double qY = 0.5;
    private double qHeading = 2.0;

    // How noisy Limelight measurements are
    private double rX = 4.0;
    private double rY = 4.0;
    private double rHeading = 9.0;

    public PoseEKF(Pose2D initialPose) {

        x = initialPose.getX(DistanceUnit.INCH);
        y = initialPose.getY(DistanceUnit.INCH);
        heading = initialPose.getHeading(AngleUnit.DEGREES);

        pX = 1;
        pY = 1;
        pHeading = 1;
    }

    public void predict(
            double velocityX,
            double velocityY,
            double angularVelocity,
            double dt) {

        x += velocityX * dt;
        y += velocityY * dt;
        heading += angularVelocity * dt;

        heading = normalizeAngle(heading);

        // Prediction becomes less certain over time
        pX += qX * dt;
        pY += qY * dt;
        pHeading += qHeading * dt;
    }

    private double normalizeAngle(double angle) {

        while (angle > 180)
            angle -= 360;

        while (angle < -180)
            angle += 360;

        return angle;
    }

    public void update(
            double visionX,
            double visionY,
            double visionHeading) {

        double errorX = visionX - x;
        double errorY = visionY - y;

        double errorHeading =
                normalizeAngle(visionHeading - heading);

        double kX = pX / (pX + rX);
        double kY = pY / (pY + rY);
        double kHeading =
                pHeading / (pHeading + rHeading);

        x += kX * errorX;
        y += kY * errorY;
        heading += kHeading * errorHeading;

        heading = normalizeAngle(heading);

        pX *= (1 - kX);
        pY *= (1 - kY);
        pHeading *= (1 - kHeading);
    }

    public Pose2D getPose() {
        return new Pose2D(
                DistanceUnit.INCH,
                x,
                y,
                AngleUnit.DEGREES,
                heading
        );
    }
}