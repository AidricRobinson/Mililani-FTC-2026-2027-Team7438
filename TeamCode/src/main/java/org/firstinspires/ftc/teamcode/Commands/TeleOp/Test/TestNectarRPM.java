package org.firstinspires.ftc.teamcode.Commands.TeleOp.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class TestNectarRPM {
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;
    private PIDController pidController;
    private Telemetry telemetry;
    public TestNectarRPM(ShooterSubsystem shooterSubsystem,
                         Gamepad gamepad,
                         Telemetry telemetry) {
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
        pidController = new PIDController(0, 0, 0, 0);
        this.telemetry = telemetry;
    }
    public void operate(Gamepad gamepad) {
        if (gamepad.a) {
            pidController.createSetPoint(1500);
            pidController.setProcessVariable(shooterSubsystem.getNectarShooterRPM());
            shooterSubsystem.setNectarShooterPower(pidController.getOutput());
            telemetry.addData("Nectar RPM: ", shooterSubsystem.getNectarShooterRPM());
            telemetry.update();
        }
        else if (gamepad.b) {
            pidController.createSetPoint(2000);
            pidController.setProcessVariable(shooterSubsystem.getNectarShooterRPM());
            shooterSubsystem.setNectarShooterPower(pidController.getOutput());
            telemetry.addData("Nectar RPM: ", shooterSubsystem.getNectarShooterRPM());
            telemetry.update();
        }
        else if (gamepad.x) {
            pidController.createSetPoint(2500);
            pidController.setProcessVariable(shooterSubsystem.getNectarShooterRPM());
            shooterSubsystem.setNectarShooterPower(pidController.getOutput());
            telemetry.addData("Nectar RPM: ", shooterSubsystem.getNectarShooterRPM());
            telemetry.update();
        }
        else if (gamepad.y) {
            pidController.createSetPoint(3000);
            pidController.setProcessVariable(shooterSubsystem.getNectarShooterRPM());
            shooterSubsystem.setNectarShooterPower(pidController.getOutput());
            telemetry.addData("Nectar RPM: ", shooterSubsystem.getNectarShooterRPM());
            telemetry.update();
        }
        else {
            shooterSubsystem.setBothShooterPower(0);
            pidController.resetIntegral();
        }
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        pidController.resetIntegral();
    }
}
