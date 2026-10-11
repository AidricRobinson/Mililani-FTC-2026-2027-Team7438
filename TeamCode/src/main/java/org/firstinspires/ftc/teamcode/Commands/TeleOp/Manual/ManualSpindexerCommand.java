package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class ManualSpindexerCommand {
    private StorageSubsystem storageSubsystem;
    private Gamepad gamepad;
    public ManualSpindexerCommand(StorageSubsystem storageSubsystem, Gamepad gamepad) {
        this.storageSubsystem = storageSubsystem;
        this.gamepad = gamepad;
    }
    public void operate() {
        if (gamepad.right_trigger >= 0.25 && gamepad.x) {
            storageSubsystem.setSpindexerPower(-1);
        }
        else if (gamepad.x) {
            storageSubsystem.setSpindexerPower(1);
        }
        else {
            storageSubsystem.setSpindexerPower(0);
        }
    }
    public void shutdown() {
        storageSubsystem.shutdown();
    }
}
