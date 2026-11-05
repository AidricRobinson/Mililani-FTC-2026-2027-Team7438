package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class ManualKickerCommand {
    private StorageSubsystem storageSubsystem;
    private Gamepad gamepad;
    public ManualKickerCommand(StorageSubsystem storageSubsystem, Gamepad gamepad) {
        this.storageSubsystem = storageSubsystem;
        this.gamepad = gamepad;
    }
    public void operate() {

        if (gamepad.left_bumper) {
            storageSubsystem.requestStoragePower(.75);
        }

    }
    public void shutdown() {
        storageSubsystem.shutdown();
    }
}
