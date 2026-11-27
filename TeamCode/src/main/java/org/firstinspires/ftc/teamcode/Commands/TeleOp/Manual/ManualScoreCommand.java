package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.SpeedPIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;

public class ManualScoreCommand {
    private TransferSubsystem transferSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private SpeedPIDController pidController;
    private Gamepad gamepad;

    public ManualScoreCommand (TransferSubsystem transferSubsystem,
                               ShooterSubsystem shooterSubsystem,
                               Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.transferSubsystem = transferSubsystem;
        this.gamepad = gamepad;

    }

    public void operate() {
        if (gamepad.left_trigger_pressed) {
//            pidController.createSetPoint(2500);
//            pidController.setProcessVariable(shooterSubsystem.getRPM());
//            shooterSubsystem.setPower(pidController.getOutput());
            shooterSubsystem.requestPower(.5);
        }
        if (gamepad.left_bumper) {
            transferSubsystem.requestBottomHarvester(1);
            transferSubsystem.requestTopHarvester(.5);
        }
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        transferSubsystem.shutdown();
    }
}
