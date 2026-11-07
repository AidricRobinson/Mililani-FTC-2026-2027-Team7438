package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;

public class TestFlowerArm {
    private PivotSubsystem pivotSubsystem;
    private Gamepad gamepad;

    public TestFlowerArm (PivotSubsystem pivotSubsystem, Gamepad gamepad) {
        this.pivotSubsystem = pivotSubsystem;
        this.gamepad = gamepad;
    }
    public void operate() {
        if (gamepad.left_bumper) {
            pivotSubsystem.setPivotDown();
        }
        else if (gamepad.right_bumper) {
            pivotSubsystem.setPivotUp();
        }
    }
    public void shutdown() {
        pivotSubsystem.shutdown();
    }
}
