package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.SpeedPIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class ManualShootCommand {
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;
    private SpeedPIDController pidController;
    public ManualShootCommand(ShooterSubsystem shooterSubsystem, Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;

        pidController = Constants.PIDConstants.kShooterPID;

    }
    public void operate() {
        if (gamepad.y) {
            shooterSubsystem.requestPower(1);
        }
        else if (gamepad.dpad_up) {
            shooterSubsystem.requestPower(-1);
        }
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
    }
}
