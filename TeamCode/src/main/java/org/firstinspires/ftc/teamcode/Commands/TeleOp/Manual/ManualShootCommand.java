package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class ManualShootCommand {
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;
    private PIDController pidController;
    public ManualShootCommand(ShooterSubsystem shooterSubsystem, Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;

        pidController = Constants.PayloadConstants.shooterPID;

    }
    public void operate(double dt) {
        if (gamepad.dpad_up && gamepad.right_trigger >= 0.25) {
            shooterSubsystem.requestPower(-1);
        }
        else if (gamepad.dpad_up) {
            pidController.createSetPoint(2000);
            pidController.setProcessVariable(shooterSubsystem.getRPM(dt));
        }
        else {
            pidController.resetIntegral();
        }
    }
    public void shutdown() {
        pidController.resetIntegral();
        shooterSubsystem.shutdown();
    }
}
