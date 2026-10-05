package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class HiveAngleSolver {

    public HiveAngleSolver() {
    }

    public Pose2D solve(LLResult result) {

        if (result == null) {
            return null;
        }

        if (!result.isValid()) {
            return null;
        }

        List<LLResultTypes.FiducialResult> tags =
                result.getFiducialResults();

        if (tags == null || tags.isEmpty()) {
            return null;
        }

        int hive = identifyHive(tags);

        if (hive == -1) {
            return null;
        }

        Pose3D mt1 = result.getBotpose();

        if (mt1 == null) {
            return null;
        }

        double mt1X = mt1.getPosition().x;
        double mt1Y = mt1.getPosition().y;
        double mt1Z = mt1.getPosition().z;
        double mt1Yaw = mt1.getOrientation().getYaw(AngleUnit.DEGREES);
        double mt1Pitch = mt1.getOrientation().getPitch(AngleUnit.DEGREES);
        double mt1Roll = mt1.getOrientation().getRoll(AngleUnit.DEGREES);

        if (!isPoseUsable(mt1)) {
            return null;
        }

        double hiveAngle = solveHiveAngle(mt1, hive);

        if (Double.isNaN(hiveAngle)) {
            return null;
        }

        Pose2D correctedPose =
                correctPose(mt1, hive, hiveAngle);

        return correctedPose;
    }

    private int identifyHive(List<LLResultTypes.FiducialResult> tags) {

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

        if (redHive && !blueHive) {
            return 0;
        }

        if (blueHive && !redHive) {
            return 1;
        }

        return -1;
    }

    private double solveHiveAngle(Pose3D mt1, int hive) {

        return Double.NaN;
    }

    private Pose2D correctPose(Pose3D mt1, int hive, double hiveAngle) {

        double correctedX = mt1.getPosition().x;
        double correctedY = mt1.getPosition().y;
        double correctedHeading =
                mt1.getOrientation().getYaw(AngleUnit.DEGREES);

        return new Pose2D(
                DistanceUnit.INCH,
                correctedX,
                correctedY,
                AngleUnit.DEGREES,
                correctedHeading
        );
    }

    private boolean isPoseUsable(Pose3D pose) {

        double roll = Math.abs(
                pose.getOrientation().getRoll(AngleUnit.DEGREES));

        double pitch = Math.abs(
                pose.getOrientation().getPitch(AngleUnit.DEGREES));

        if (roll > 10) return false;
        if (pitch > 10) return false;

        return true;
    }

    public int getTagCount(LLResult result) {

        if (result == null ||
                result.getFiducialResults() == null) {
            return 0;
        }

        return result.getFiducialResults().size();
    }

    public boolean hasTags(LLResult result) {
        return getTagCount(result) > 0;
    }
}