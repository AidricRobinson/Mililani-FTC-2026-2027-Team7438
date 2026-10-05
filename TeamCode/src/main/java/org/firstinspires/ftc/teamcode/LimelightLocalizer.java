package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class LimelightLocalizer {

    private Limelight3A limelight;
    private LLResult result;

    public LimelightLocalizer(Limelight3A limelight) {
        this.limelight = limelight;
    }

    public void update(PinpointLocalizer pinpoint) {
        limelight.updateRobotOrientation(pinpoint.getHeading());
        result = limelight.getLatestResult();
    }

    public LLResult getLatestResult() {
        return result;
    }

    public Pose3D getMT1() {
        return result.getBotpose();
    }

    public Pose3D getMT2() {
        return result.getBotpose_MT2();
    }

    public List<LLResultTypes.FiducialResult> getTags() {
        return result.getFiducialResults();
    }

    public int getTagCount() {
        return getTags().size();
    }
}