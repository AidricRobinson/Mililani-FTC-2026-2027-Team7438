package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.SpeedPIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;

public class TestLimelightShootCommand {
    private ShooterSubsystem shooterSubsystem;
    private TransferSubsystem transferSubsystem;
    private Limelight3A limelight;
    private Gamepad gamepad;
    private LLResult result;
    private SpeedPIDController pidController;
    double x;

    public TestLimelightShootCommand(ShooterSubsystem shooterSubsystem,
                                     TransferSubsystem transferSubsystem,
                                     Limelight3A limelight,
                                     Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.transferSubsystem = transferSubsystem;
        this.limelight = limelight;
        this.gamepad = gamepad;

        pidController = Constants.PIDConstants.kShooterPID;

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
            pidController.setProcessVariable(shooterSubsystem.getRPM());
            shooterSubsystem.setPower(pidController.getOutput());
        }
        else {
            shooterSubsystem.shutdown();
        }
    }


}
