package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;

public class ManualPivotCommand {
    private PivotSubsystem pivotSubsystem;
    private Gamepad gamepad;

    public ManualPivotCommand(PivotSubsystem pivotSubsystem, Gamepad gamepad) {
        this.pivotSubsystem = pivotSubsystem;
        this.gamepad = gamepad;
    }

    public void operate() {
        if (gamepad.dpad_left) {
            pivotSubsystem.setPivotUp();
        }
        else if (gamepad.dpad_right) {
            pivotSubsystem.setPivotDown();
        }





//        if (gamepad.dpad_left) {
//            pivotSubsystem.setPivotDown();
//        }
//        else {
//            pivotSubsystem.setPivotUp();
//        }
    }
    public void shutdown() {
        pivotSubsystem.shutdown();
    }
}
