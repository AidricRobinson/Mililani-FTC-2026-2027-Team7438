package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class ManualShootCommand {
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;
    private PIDController nectarPID;
    private PIDController pollenPID;
    public ManualShootCommand(ShooterSubsystem shooterSubsystem, Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
        nectarPID = Constants.PayloadConstants.nectarPID;
        pollenPID = Constants.PayloadConstants.pollenPID;
    }
    public void operate() {
        if (gamepad.dpad_up && gamepad.right_trigger >= 0.25) {
            if (shooterSubsystem.getNectarToggle()) {
                shooterSubsystem.setNectarShooterPower(-0.9);
            }
            if (shooterSubsystem.getPollenToggle()) {
                shooterSubsystem.setPollenShooterPower(-0.9);
            }
        }
        else if (gamepad.dpad_up) {
            if (shooterSubsystem.getNectarToggle()) {
                nectarPID.createSetPoint(4000);
                nectarPID.setProcessVariable(shooterSubsystem.getNectarShooterRPM());
                shooterSubsystem.setNectarShooterPower(nectarPID.getOutput());
            }
            if (shooterSubsystem.getPollenToggle()) {
                pollenPID.createSetPoint(4000);
                pollenPID.setProcessVariable(shooterSubsystem.getPollenShooterRPM());
                shooterSubsystem.setPollenShooterPower(pollenPID.getOutput());
            }
        }
        else {
            pollenPID.resetIntegral();
            nectarPID.resetIntegral();
            shooterSubsystem.setBothShooterPower(0);
        }
    }
    public void shutdown() {
        pollenPID.resetIntegral();
        nectarPID.resetIntegral();
        shooterSubsystem.shutdown();
    }
}
