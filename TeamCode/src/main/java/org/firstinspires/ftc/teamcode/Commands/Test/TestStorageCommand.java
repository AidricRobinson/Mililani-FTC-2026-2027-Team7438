package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class TestStorageCommand {
    private StorageSubsystem storageSubsystem;
    private Gamepad gamepad;
    public TestStorageCommand(StorageSubsystem storageSubsystem, Gamepad gamepad){
        this.storageSubsystem = storageSubsystem;
        this.gamepad = gamepad;
        storageSubsystem.setTestPower(0.5);
    }

    public void operate(){
        if (gamepad.dpad_up) {
            storageSubsystem.setStoragePower(1);
        }
        if (gamepad.dpad_left) {
            storageSubsystem.setStoragePower(.7);
        }
        if (gamepad.dpad_right) {
            storageSubsystem.setStoragePower(.6);
        }
        if (gamepad.dpad_down) {
            storageSubsystem.setStoragePower(.5);
        }
        else {
            storageSubsystem.setStoragePower(0);
        }
    }

    public void shutdown(){
        storageSubsystem.shutdown();
    }
}

