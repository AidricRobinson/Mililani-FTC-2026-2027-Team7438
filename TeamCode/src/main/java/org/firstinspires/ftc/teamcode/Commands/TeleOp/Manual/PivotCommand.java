package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;

public class PivotCommand {
    PivotSubsystem pivotSubsystem;
    Gamepad gamepad;
    boolean out = false;

    public PivotCommand(PivotSubsystem pivotSubsystem, Gamepad gamepad){
        this.pivotSubsystem = pivotSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Gamepad gamepad){

        if(gamepad.left_trigger > .2 && !out){
            out = true;
        }
        else if(gamepad.left_trigger > .2 && out){
            out = false;
        }

        if(out){
            pivotSubsystem.setPosition(1);
        }
        else if(!out){
            pivotSubsystem.setPosition(0);
        }
    }
    public void shutdown(){
        pivotSubsystem.shutdown();
    }
}
