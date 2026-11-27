package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;

public class TestTransferCommand {
    private TransferSubsystem transferSubsystem;
    private Gamepad gamepad;
    public TestTransferCommand(TransferSubsystem transferSubsystem, Gamepad gamepad){
        this.transferSubsystem = transferSubsystem;
        this.gamepad = gamepad;
        transferSubsystem.setTestPower(0.5);
    }

    public void operate(){
        if (gamepad.dpad_down) {
            transferSubsystem.setBottomHarvesterPower(1);
            transferSubsystem.setTopHarvesterPower(.7);
        }
        else if (gamepad.dpad_right) {
            transferSubsystem.setBottomHarvesterPower(1);
            transferSubsystem.setTopHarvesterPower(.8);
        }
        else if (gamepad.x) {
            transferSubsystem.setBottomHarvesterPower(1);
            transferSubsystem.setTopHarvesterPower(.9);
        }
        else if (gamepad.y) {
            transferSubsystem.setBottomHarvesterPower(1);
            transferSubsystem.setTopHarvesterPower(1);
        }
        else {
            transferSubsystem.shutdown();
        }
    }

    public void shutdown(){
        transferSubsystem.shutdown();
    }
}

