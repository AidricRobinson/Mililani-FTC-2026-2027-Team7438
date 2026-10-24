package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class TestLimelightShootCommand {
    private ShooterSubsystem shooterSubsystem;
    private StorageSubsystem storageSubsystem;
    private Limelight3A limelight;
    private Gamepad gamepad;
    private LLResult result;
    private PIDController pidController;
    double x;

    public TestLimelightShootCommand(ShooterSubsystem shooterSubsystem,
                                     StorageSubsystem storageSubsystem,
                                     Limelight3A limelight,
                                     Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.limelight = limelight;
        this.gamepad = gamepad;

        pidController = Constants.PayloadConstants.shooterPID;

        x = 0;
    }
    public void operate(double dt) {
        result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            x = result.getTa();
        }
        if (gamepad.right_bumper) {
            pidController.createSetPoint(Constants.PinpointConstants.kLimeA * Math.pow(x, 2)
            + Constants.PinpointConstants.kLimeB * x
            + Constants.PinpointConstants.kLimeC);
            pidController.setProcessVariable(shooterSubsystem.getRPM(dt));
            shooterSubsystem.setPower(pidController.getOutput(dt));
        }
        else {
            pidController.resetIntegral();
            shooterSubsystem.shutdown();
        }
    }


}
