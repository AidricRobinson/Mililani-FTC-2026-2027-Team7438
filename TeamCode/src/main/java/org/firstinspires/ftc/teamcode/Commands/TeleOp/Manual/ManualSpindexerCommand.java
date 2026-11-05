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
        if (gamepad.dpad_up) {
            storageSubsystem.requestSpindexerPower(-1);
        }
        else if (gamepad.y) {
            storageSubsystem.requestSpindexerPower(1);
        }

    }
    public void shutdown() {
        storageSubsystem.shutdown();
    }
}
