package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;

public class ManualScoreCommand {
    private TransferSubsystem transferSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;

    public ManualScoreCommand (TransferSubsystem transferSubsystem,
                               ShooterSubsystem shooterSubsystem,
                               Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.transferSubsystem = transferSubsystem;
        this.gamepad = gamepad;
    }

    public void operate() {
        if (gamepad.right_trigger_pressed) {
//            shooterSubsystem.setRPM(2500);
            shooterSubsystem.requestPower(.5);
        }
        if (gamepad.left_trigger_pressed) {
            transferSubsystem.requestBottomHarvester(1);
            transferSubsystem.requestTopHarvester(.5);
        }
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        transferSubsystem.shutdown();
    }
}
