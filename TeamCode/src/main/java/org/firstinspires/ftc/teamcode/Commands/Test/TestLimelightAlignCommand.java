package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class TestLimelightAlignCommand {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private Limelight3A limelight;
    private LLResult result;
    public TestLimelightAlignCommand(MecanumDriveSubsystem mecanumDriveSubsystem, Limelight3A limelight) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.limelight = limelight;
    }

    public void operate(Gamepad gamepad) {
        result = limelight.getLatestResult();
        double tx = 0;
        if (result != null && result.isValid()) {
            tx = result.getTx(); // How far left or right the target is (degrees)
        } else {
            tx = 30;
        }
        while(Math.abs(tx) > 0.5 && gamepad.left_bumper) {
            result = limelight.getLatestResult();
            tx = 0;
            if (result != null && result.isValid()) {
                tx = result.getTx(); // How far left or right the target is (degrees)
            } else {
                tx = 0;
            }
            double kp = 0.7;
            double KFF = 0.05;
            double output = kp * (tx) + KFF;

            mecanumDriveSubsystem.setLeftFrontPower(output);
            mecanumDriveSubsystem.setLeftBackPower(output);
            mecanumDriveSubsystem.setRightBackPower(-output);
            mecanumDriveSubsystem.setRightFrontPower(-output);
            //Renewing the tx
            result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                tx = result.getTx(); // How far left or right the target is (degrees)
            } else {
                tx = 0;
            }
        }
        mecanumDriveSubsystem.shutdown();

    }
    public void shutdown() {
        mecanumDriveSubsystem.shutdown();
    }
}
