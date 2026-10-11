package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class LimelightToggle {
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;


    public LimelightToggle(ShooterSubsystem shooterSubsystem, Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
    }
    public void operate() {
        if (gamepad.y && gamepad.left_trigger >= 0.25) {
            shooterSubsystem.setManualToggle(true);
        }
        else {
            shooterSubsystem.setManualToggle(false);
        }
    }
    public void shutdown() {
        shooterSubsystem.setManualToggle(false);
    }
}
