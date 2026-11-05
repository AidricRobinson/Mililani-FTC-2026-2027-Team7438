package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class ManualScoreCommand {
    private StorageSubsystem storageSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;

    public ManualScoreCommand (StorageSubsystem storageSubsystem,
                               ShooterSubsystem shooterSubsystem,
                               Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.gamepad = gamepad;
    }

    public void operate() {
        if (gamepad.right_trigger_pressed) {
//            shooterSubsystem.setRPM(2500);
            shooterSubsystem.requestPower(.5);
        }
        if (gamepad.left_trigger_pressed) {
            storageSubsystem.requestSpindexerPower(-1);
            storageSubsystem.requestStoragePower(.8);
        }
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
    }
}
