package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

public class ManualIntakeCommand {
    private IntakeSubsystem intakeSubsystem;
    private Gamepad gamepad;

    public ManualIntakeCommand(IntakeSubsystem intakeSubsystem, Gamepad gamepad) {
        this.intakeSubsystem = intakeSubsystem;
        this.gamepad = gamepad;
    }
    public void operate() {
        if (gamepad.dpad_down) {
            intakeSubsystem.requestPower(-.5); // Test input value
        }
        else if (gamepad.a) {
            intakeSubsystem.requestPower(.25); // Test input value
        }
        else {
            intakeSubsystem.setIntakePower(0);
        }
    }
    public void shutdown() {
        intakeSubsystem.shutdown();
    }
}
