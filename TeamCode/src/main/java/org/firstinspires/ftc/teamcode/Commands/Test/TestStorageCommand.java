package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class TestStorageCommand {
    private StorageSubsystem storageSubsystem;
    private Gamepad gamepad;
    public TestStorageCommand(StorageSubsystem storageSubsystem, Gamepad gamepad){
        this.storageSubsystem = storageSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Gamepad gamepad, double spindexerPower, double kickerPower){
        if(gamepad.left_bumper){
            storageSubsystem.setStoragePower(kickerPower);
        }
        else {
            storageSubsystem.setStoragePower(0);
        }
        if (gamepad.right_bumper) {
            storageSubsystem.setSpindexerPower(spindexerPower);
        }
        else {
            storageSubsystem.setSpindexerPower(0);
        }
    }

    public void shutdown(){
        storageSubsystem.shutdown();
    }
}

