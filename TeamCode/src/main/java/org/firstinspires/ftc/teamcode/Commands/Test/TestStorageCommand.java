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

    public void operate(Gamepad gamepad, double spindexerPower, double kickerPower){
        if (gamepad.dpad_up) {
            storageSubsystem.increasePower(0.1);
        }
        if (gamepad.dpad_down) {
            storageSubsystem.decreasePower(0.05);
        }
        if (gamepad.dpad_right) {
            storageSubsystem.setTestPower(0.5);
        }
        if (gamepad.dpad_left) {
            storageSubsystem.setStoragePower(storageSubsystem.getTestPower());
            storageSubsystem.setSpindexerPower(1);
        }
        else {
            storageSubsystem.shutdown();
        }
    }

    public void shutdown(){
        storageSubsystem.shutdown();
    }
}

