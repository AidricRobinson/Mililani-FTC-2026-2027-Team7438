package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;

public class PivotCommand {
    private PivotSubsystem pivotSubsystem;
    private Gamepad gamepad;
    private boolean out = false;
    private boolean lastLeftBumper = false;

    public PivotCommand(PivotSubsystem pivotSubsystem, Gamepad gamepad){
        this.pivotSubsystem = pivotSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Gamepad gamepad){

        if (gamepad.left_bumper && !lastLeftBumper) {
            out = !out;
        }
        lastLeftBumper = gamepad.left_bumper;
        if (out) {
            pivotSubsystem.setPosition(1);
        } else {
            pivotSubsystem.setPosition(0);
        }
    }
    public void shutdown(){
        pivotSubsystem.shutdown();
    }
}
