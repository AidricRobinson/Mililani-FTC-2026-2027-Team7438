package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;

public class ManualTransferCommand {
    private TransferSubsystem transferSubsystem;
    private Gamepad gamepad;

    public ManualTransferCommand(TransferSubsystem transferSubsystem, Gamepad gamepad) {
        this.transferSubsystem = transferSubsystem;
        this.gamepad = gamepad;
    }
    public void operate() {
        if (gamepad.right_trigger_pressed || gamepad.right_bumper) {
            transferSubsystem.requestTopHarvester(-1);
            transferSubsystem.requestBottomHarvester(-1);
        }
        else if (gamepad.x) {
            transferSubsystem.requestTopHarvester(1);
        }
        else if (gamepad.dpad_left) {
            transferSubsystem.requestTopHarvester(-1);
        }
        else if (gamepad.b){
            transferSubsystem.requestBottomHarvester(1);
        }
        else if (gamepad.dpad_right) {
            transferSubsystem.requestBottomHarvester(-1);
        }
    }
}
