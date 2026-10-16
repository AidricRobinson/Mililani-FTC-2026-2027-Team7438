package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoShooterCommand {
    ShooterSubsystem shooterSubsystem;
    StorageSubsystem storageSubsystem;
    ElapsedTime elapsedTime;
    double startTime;

    public AutoShooterCommand(ShooterSubsystem shooterSubsystem, StorageSubsystem storageSubsystem, ElapsedTime elapsedTime){
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.elapsedTime = elapsedTime;
    }

    public void pollenOperate(double power, int time){
        startTime = elapsedTime.milliseconds();
        while(elapsedTime.milliseconds() < startTime + time){
            shooterSubsystem.setPollenShooterPower(power);
            storageSubsystem.setStoragePower(.76);
            storageSubsystem.setSpindexerPower(1);
        }
    }

    public void operate(double power, int time){
        startTime = elapsedTime.milliseconds();
        while(elapsedTime.milliseconds() < startTime + time){
            shooterSubsystem.setNectarShooterPower(power);
            storageSubsystem.setStoragePower(.767);
            storageSubsystem.setSpindexerPower(1);
        }
    }

    public void shutdown(){
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
    }
}
