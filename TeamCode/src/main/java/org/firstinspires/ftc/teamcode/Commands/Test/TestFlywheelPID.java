package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.SpeedPIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class TestFlywheelPID {
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;
    private SpeedPIDController pidController;

    public TestFlywheelPID (ShooterSubsystem shooterSubsystem, Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;

        pidController = Constants.PIDConstants.kShooterPID;
    }

    public void operate(Telemetry telemetry) {
        pidController.setProcessVariable(2500);
        pidController.setProcessVariable(shooterSubsystem.getRPM());
        if (gamepad.x) {
            shooterSubsystem.setPower(pidController.getOutput());
        }
        else {
            shooterSubsystem.shutdown();
        }
        telemetry.addData("SetPoint: ", 2500);
        telemetry.addData("RPM: ", shooterSubsystem.getRPM());
        telemetry.addData("Error: ", pidController.getError());
        telemetry.addData("Output: ", pidController.getOutput());
        telemetry.update();


    }
    public void shutdown() {
        shooterSubsystem.shutdown();
    }
}
