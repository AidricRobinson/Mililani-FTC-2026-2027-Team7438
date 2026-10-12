package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

public class TestIntakeCommand {
    private IntakeSubsystem intakeSubsystem;
    private Gamepad gamepad;
//    Gamepad gamepad2;  // if we need another gamepad for this command

    public TestIntakeCommand(IntakeSubsystem intakeSubsystem, Gamepad gamepad){
        this.intakeSubsystem = intakeSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Gamepad gamepad){
        if (gamepad.y){
            intakeSubsystem.setIntakePower(.6);
        }

        else if (gamepad.x){
            intakeSubsystem.setIntakePower(.30);  //decimal values as the power increments in percentages, with 1 as max, or 100%
        }

        else{
            intakeSubsystem.setIntakePower(0);
        }

    }

    public void shutdown(){
        intakeSubsystem.shutdown();
    }
}
