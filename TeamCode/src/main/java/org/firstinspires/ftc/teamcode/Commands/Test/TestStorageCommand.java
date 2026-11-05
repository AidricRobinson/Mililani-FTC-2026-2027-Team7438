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
            storageSubsystem.requestStoragePower(1);
        }
        else if (gamepad.dpad_left) {
            storageSubsystem.requestStoragePower(.7);
        }
        else if (gamepad.dpad_right) {
            storageSubsystem.requestStoragePower(.6);
        }
        else if (gamepad.dpad_down) {
            storageSubsystem.requestStoragePower(.5);
        }

    }

    public void shutdown(){
        storageSubsystem.shutdown();
    }
}

