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
        if (gamepad.a) {
            transferSubsystem.setTopHarvesterPower(.5);
        }
        else if (gamepad.b) {
            transferSubsystem.setTopHarvesterPower(.25);
        }
        else if (gamepad.x) {
            transferSubsystem.setTopHarvesterPower(.75);
        }
        else if (gamepad.y) {
            transferSubsystem.setTopHarvesterPower(1);
        }
    }

    public void shutdown(){
        transferSubsystem.shutdown();
    }
}

