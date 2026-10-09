package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class HiveAngleSolver {

    private static final double MAX_ROLL = 10.0;
    private static final double MAX_PITCH = 10.0;

    // Assumed HIVE pivot coordinates in the same frame as the MT1 pose.
    // These values must be verified against the actual field-map origin.
    private static final double PIVOT_Y = 0.0;
    private static final double PIVOT_Z = 43.95 * 0.0254;

    // Assumed maximum HIVE rotation from its balanced position.
    private static final double MAX_ANGLE = Math.toRadians(30.0);

    // The field-map coordinates are in meters, while Pose2D is returned in inches.
    private static final double METERS_TO_INCHES = 39.37007874;

    public HiveAngleSolver() {
    }

    // Processes the latest Limelight result and returns a corrected robot pose.
    public Pose2D solve(LLResult result) {
        if (result == null || !result.isValid()) {
            return null;
        }

        List<LLResultTypes.FiducialResult> tags =
                result.getFiducialResults();

        if (tags == null || tags.isEmpty()) {
            return null;
        }

        // Determine which HIVE's tags are visible.
        int hive = identifyHive(tags);

        if (hive == -1) {
            return null;
        }

        // MT1 uses the configured field map to estimate the robot pose.
        Pose3D mt1 = result.getBotpose();

        if (mt1 == null || !isPoseUsable(mt1)) {
            return null;
        }

        // Estimate the HIVE rotation from the MT1 pose.
        double hiveAngle = solveHiveAngle(mt1, tags, hive);

        if (Double.isNaN(hiveAngle)) {
            return null;
        }

        // Apply the estimated correction to the MT1 pose.
        return correctPose(mt1, hive, hiveAngle);
    }

    // Returns 0 for the red HIVE, 1 for the blue HIVE, or -1 if ambiguous.
    private int identifyHive(
            List<LLResultTypes.FiducialResult> tags) {

        boolean redHive = false;
        boolean blueHive = false;

        for (LLResultTypes.FiducialResult tag : tags) {
            int id = tag.getFiducialId();

            if (id >= 30 && id <= 37) {
                redHive = true;
            }

            if (id >= 38 && id <= 45) {
                blueHive = true;
            }
        }

        // Reject a frame containing tags from both groups.
        if (redHive && !blueHive) {
            return 0;
        }

        if (blueHive && !redHive) {
            return 1;
        }

        return -1;
    }

    // Attempts to solve the HIVE angle using the assumption that the robot's
    // actual vertical position is approximately zero.
    //
    // IMPORTANT: This calculation assumes a specific pivot coordinate system.
    // It has not been validated against the complete field map or mechanism.
    private double solveHiveAngle(
            Pose3D mt1,
            List<LLResultTypes.FiducialResult> tags,
            int hive) {

        if (mt1 == null || tags == null || tags.isEmpty()) {
            return Double.NaN;
        }

        double y = mt1.getPosition().y;
        double z = mt1.getPosition().z;

        // Translate the estimated pose relative to the assumed pivot.
        double a = y - PIVOT_Y;
        double b = z - PIVOT_Z;

        double radius = Math.hypot(a, b);

        if (radius < 1e-9) {
            return Double.NaN;
        }

        // Solve the circle/rotation equation for candidate angles.
        double target = -PIVOT_Z / radius;

        if (target < -1.0 || target > 1.0) {
            return Double.NaN;
        }

        double alpha = Math.asin(target);
        double phi = Math.atan2(b, a);

        double angle1 = normalizeRadians(alpha - phi);
        double angle2 = normalizeRadians(Math.PI - alpha - phi);

        // Keep only candidates inside the assumed mechanical rotation range.
        boolean valid1 = Math.abs(angle1) <= MAX_ANGLE;
        boolean valid2 = Math.abs(angle2) <= MAX_ANGLE;

        if (valid1 && valid2) {
            return Math.abs(angle1) <= Math.abs(angle2)
                    ? angle1 : angle2;
        }

        if (valid1) {
            return angle1;
        }

        if (valid2) {
            return angle2;
        }

        return Double.NaN;
    }

    // Converts the MT1 pose into a Pose2D after applying the assumed inverse
    // HIVE rotation. The transform must be verified for the real coordinate frame.
    private Pose2D correctPose(
            Pose3D mt1,
            int hive,
            double hiveAngle) {

        if (mt1 == null || Double.isNaN(hiveAngle)
                || Double.isInfinite(hiveAngle)) {
            return null;
        }

        double x = mt1.getPosition().x;
        double y = mt1.getPosition().y;
        double z = mt1.getPosition().z;

        double cos = Math.cos(hiveAngle);
        double sin = Math.sin(hiveAngle);

        // Apply the assumed inverse rotation in the Y-Z plane.
        double correctedX = x;
        double correctedY =
                PIVOT_Y + cos * (y - PIVOT_Y)
                        - sin * (z - PIVOT_Z);

        double yaw = Math.toRadians(
                mt1.getOrientation().getYaw(AngleUnit.DEGREES));

        double pitch = Math.toRadians(
                mt1.getOrientation().getPitch(AngleUnit.DEGREES));

        // Estimate corrected heading from the rotated orientation.
        double correctedHeading = Math.atan2(
                cos * Math.sin(yaw) * Math.cos(pitch)
                        + sin * Math.sin(pitch),
                Math.cos(yaw) * Math.cos(pitch));

        return new Pose2D(
                DistanceUnit.INCH,
                correctedX * METERS_TO_INCHES,
                correctedY * METERS_TO_INCHES,
                AngleUnit.DEGREES,
                Math.toDegrees(correctedHeading)
        );
    }

    // Wraps an angle into the range [-pi, pi].
    private double normalizeRadians(double angle) {
        while (angle > Math.PI) {
            angle -= 2.0 * Math.PI;
        }

        while (angle < -Math.PI) {
            angle += 2.0 * Math.PI;
        }

        return angle;
    }

    // Rejects poses with unusually large roll or pitch.
    private boolean isPoseUsable(Pose3D pose) {
        double roll = Math.abs(
                pose.getOrientation().getRoll(AngleUnit.DEGREES));

        double pitch = Math.abs(
                pose.getOrientation().getPitch(AngleUnit.DEGREES));

        return roll <= MAX_ROLL && pitch <= MAX_PITCH;
    }

    // Returns the number of AprilTags detected in the current result.
    public int getTagCount(LLResult result) {
        if (result == null || result.getFiducialResults() == null) {
            return 0;
        }

        return result.getFiducialResults().size();
    }

    // Checks whether at least one AprilTag was detected.
    public boolean hasTags(LLResult result) {
        return getTagCount(result) > 0;
    }

    // Returns the detected HIVE ID, or -1 if the result is invalid or ambiguous.
    public int getDetectedHive(LLResult result) {
        if (result == null || !result.isValid()) {
            return -1;
        }

        List<LLResultTypes.FiducialResult> tags =
                result.getFiducialResults();

        if (tags == null || tags.isEmpty()) {
            return -1;
        }

        return identifyHive(tags);
    }
}