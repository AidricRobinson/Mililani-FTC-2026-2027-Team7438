package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

public class TestIntakeCommand {
    private IntakeSubsystem intakeSubsystem;
    private Gamepad gamepad;
    private boolean toggle;

//    Gamepad gamepad2;  // if we need another gamepad for this command

    public TestIntakeCommand(IntakeSubsystem intakeSubsystem, Gamepad gamepad){
        this.intakeSubsystem = intakeSubsystem;
        this.gamepad = gamepad;
        toggle = true;
    }

    public void operate(Telemetry telemetry){
//        if (gamepad.y && toggle) {
//            intakeSubsystem.increaseTestPower(.05);
//            toggle = false;
//        }
//        else if (gamepad.a && toggle) {
//            intakeSubsystem.decreaseTestPower(.05);
//            toggle = false;
//        }
//        else {
//            toggle = true;
//        }
//
//        if (gamepad.x) {
//            intakeSubsystem.setTestPower();
//        }
//        else {
//            intakeSubsystem.shutdown();
//        }




        if (gamepad.y) {
            intakeSubsystem.setIntakePower(1);
        }
        else if (gamepad.x) {
            intakeSubsystem.setIntakePower(.9);
        }
        else if (gamepad.b){
            intakeSubsystem.setIntakePower(.8);
        }
        else if (gamepad.a) {
            intakeSubsystem.setIntakePower(.7);
        }
        else {
            intakeSubsystem.setIntakePower(0);
        }

        telemetry.addData("Intake test power: ", intakeSubsystem.getTestPower());
        telemetry.update();
    }

    public void shutdown(){
        intakeSubsystem.shutdown();
    }
}