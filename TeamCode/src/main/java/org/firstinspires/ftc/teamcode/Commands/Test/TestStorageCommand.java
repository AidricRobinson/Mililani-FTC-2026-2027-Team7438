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
        if (gamepad.x) {
            storageSubsystem.requestSpindexerPower(1);
        }
        else if (gamepad.dpad_left) {
            storageSubsystem.requestSpindexerPower(-1);
        }

        if (gamepad.b) {
            storageSubsystem.requestStoragePower(.5);
        }
        else if (gamepad.dpad_right) {
            storageSubsystem.requestStoragePower(-.5);
        }

    }

    public void shutdown(){
        storageSubsystem.shutdown();
    }
}

