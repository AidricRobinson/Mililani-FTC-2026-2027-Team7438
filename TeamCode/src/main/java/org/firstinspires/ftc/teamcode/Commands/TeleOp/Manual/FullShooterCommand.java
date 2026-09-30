package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class FullShooterCommand {
    ShooterSubsystem shooterSubsystem;
    Gamepad gamepad;

    public FullShooterCommand(ShooterSubsystem shooterSubsystem, Gamepad gamepad){
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Gamepad gamepad){
        if(gamepad.right_trigger >= .01){
            shooterSubsystem.setBothShooterPower(.65);
        }
        else if(gamepad.left_trigger >= .01){
            shooterSubsystem.setBothShooterPower(.45);
        }
        else{
            shooterSubsystem.setBothShooterPower(0);
        }
    }

    public void shutdown(){
        shooterSubsystem.shutdown();
    }
}
