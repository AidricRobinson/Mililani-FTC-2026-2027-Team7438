package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class PoseEKF {

    // The robot's current estimated position and heading.
    // Position is measured in inches, and heading is measured in degrees.
    private double x;
    private double y;
    private double heading;

    // Covariance: how uncertain the filter is about its current estimate.
    // Larger values mean less confidence in the estimated pose.
    private double pX;
    private double pY;
    private double pHeading;

    // Process noise: how much uncertainty odometry adds per second.
    // Increase these values if odometry is less reliable.
    private double qX = 0.5;
    private double qY = 0.5;
    private double qHeading = 2.0;

    // Measurement noise: how uncertain Limelight measurements are.
    // Larger values make the filter trust Limelight less.
    private double rX = 4.0;
    private double rY = 4.0;
    private double rHeading = 9.0;

    public PoseEKF(Pose2D initialPose) {

        // Initialize the estimated pose using the starting pose.
        x = initialPose.getX(DistanceUnit.INCH);
        y = initialPose.getY(DistanceUnit.INCH);
        heading = initialPose.getHeading(AngleUnit.DEGREES);

        // Start with a small amount of uncertainty.
        pX = 1;
        pY = 1;
        pHeading = 1;
    }

    // PREDICTION STEP:
    // Use odometry velocities to predict where the robot has moved.
    // dt is the elapsed time in seconds since the previous prediction.
    public void predict(
            double velocityX,
            double velocityY,
            double angularVelocity,
            double dt) {

        // Estimate the new position using velocity multiplied by time.
        x += velocityX * dt;
        y += velocityY * dt;

        // Estimate the new heading using angular velocity and elapsed time.
        heading += angularVelocity * dt;

        // Keep the heading between -180 and 180 degrees.
        heading = normalizeAngle(heading);

        // Increase uncertainty because odometry errors accumulate over time.
        pX += qX * dt;
        pY += qY * dt;
        pHeading += qHeading * dt;
    }

    // Wrap an angle into the range [-180, 180] degrees.
    // This prevents heading differences from taking the longer route
    // around the circle.
    private double normalizeAngle(double angle) {

        while (angle > 180)
            angle -= 360;

        while (angle < -180)
            angle += 360;

        return angle;
    }

    // CORRECTION STEP:
    // Compare the Limelight pose with the predicted pose and use the
    // difference to correct the robot's estimated position and heading.
    public void update(
            double visionX,
            double visionY,
            double visionHeading) {

        // Calculate the difference between vision and the current estimate.
        double errorX = visionX - x;
        double errorY = visionY - y;

        // Normalize the heading error to use the shortest angular difference.
        double errorHeading =
                normalizeAngle(visionHeading - heading);

        // Calculate the Kalman gain for each measurement.
        // A higher gain means the filter trusts the vision measurement more.
        double kX = pX / (pX + rX);
        double kY = pY / (pY + rY);
        double kHeading =
                pHeading / (pHeading + rHeading);

        // Correct the estimated pose using the measurement errors.
        x += kX * errorX;
        y += kY * errorY;
        heading += kHeading * errorHeading;

        // Keep the corrected heading within the normal angle range.
        heading = normalizeAngle(heading);

        // Reduce uncertainty after incorporating the vision measurement.
        pX *= (1 - kX);
        pY *= (1 - kY);
        pHeading *= (1 - kHeading);
    }

    // Return the filter's current estimated pose as a Pose2D object.
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
