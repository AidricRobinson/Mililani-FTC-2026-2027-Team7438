package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;

public class PivotCommand {
    private PivotSubsystem pivotSubsystem;
    private Gamepad gamepad;
    private boolean toggle1 = true;
    private boolean toggle2 = true;

    public PivotCommand(PivotSubsystem pivotSubsystem, Gamepad gamepad){
        this.pivotSubsystem = pivotSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(){

        if(gamepad.dpad_left && toggle1 && toggle2)
        {
            pivotSubsystem.setPivotDown();
            toggle1 = false;
            toggle2 = false;
        }
        else if(gamepad.dpad_left && !toggle1 && toggle2)
        {
            pivotSubsystem.setPivotUp();
            toggle1 = true;
            toggle2 = false;

        }
        else if (!gamepad.right_bumper && !toggle2) {
            toggle2 = true;
        }
    }
    public void shutdown(){
        pivotSubsystem.shutdown();
    }
}
