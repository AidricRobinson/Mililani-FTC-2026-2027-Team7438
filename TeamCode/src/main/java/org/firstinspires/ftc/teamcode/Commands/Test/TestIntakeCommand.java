package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

public class TestIntakeCommand {
    private IntakeSubsystem intakeSubsystem;
    private Gamepad gamepad;

//    Gamepad gamepad2;  // if we need another gamepad for this command

    public TestIntakeCommand(IntakeSubsystem intakeSubsystem, Gamepad gamepad){
        this.intakeSubsystem = intakeSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Telemetry telemetry){
        if (gamepad.y) {
            intakeSubsystem.increaseTestPower(.05);
        }
        if (gamepad.a) {
            intakeSubsystem.decreaseTestPower(.05);
        }
        if (gamepad.x) {
            intakeSubsystem.setTestPower();
        }
        else {
            intakeSubsystem.shutdown();
        }
        if (gamepad.b) {
            intakeSubsystem.zeroTestPower();
        }

        telemetry.addData("Intake test power: ", intakeSubsystem.getTestPower());
        telemetry.update();
    }

    public void shutdown(){
        intakeSubsystem.shutdown();
    }
}