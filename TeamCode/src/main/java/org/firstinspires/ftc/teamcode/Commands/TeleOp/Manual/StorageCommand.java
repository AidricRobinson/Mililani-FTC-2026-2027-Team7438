package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class StorageCommand {
    StorageSubsystem storageSubsystem;
    Gamepad gamepad;
    public StorageCommand(StorageSubsystem storageSubsystem, Gamepad gamepad){
        this.storageSubsystem = storageSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Gamepad gamepad){
        if(gamepad.left_bumper){
            storageSubsystem.setStoragePower(.25);
            storageSubsystem.setSpindexerPower(1);
        }
        else if(gamepad.right_bumper){
            storageSubsystem.setStoragePower(.65);
            storageSubsystem.setSpindexerPower(1);
        }
        else{
            storageSubsystem.setStoragePower(0);
            storageSubsystem.setSpindexerPower(0);
        }
    }

    public void shutdown(){
        storageSubsystem.shutdown();
    }
}
