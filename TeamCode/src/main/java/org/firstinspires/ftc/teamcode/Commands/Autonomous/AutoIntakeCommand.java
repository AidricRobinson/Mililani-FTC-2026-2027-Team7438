package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoIntakeCommand {
    IntakeSubsystem intakeSubsystem;
    StorageSubsystem storageSubsystem;
    ElapsedTime elapsedTime;
    double startTime;

    public AutoIntakeCommand(IntakeSubsystem intakeSubsystem, StorageSubsystem storageSubsystem, ElapsedTime elapsedTime){
        this.intakeSubsystem = intakeSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.elapsedTime = elapsedTime;
    }

    public void operate(double power, int time){
        startTime = elapsedTime.milliseconds();
        while(elapsedTime.milliseconds() < startTime + time){
            intakeSubsystem.setIntakePower(power);
            storageSubsystem.setStoragePower(.2);
        }
    }

    public void shutdown(){
        intakeSubsystem.shutdown();
        storageSubsystem.shutdown();
    }

}
