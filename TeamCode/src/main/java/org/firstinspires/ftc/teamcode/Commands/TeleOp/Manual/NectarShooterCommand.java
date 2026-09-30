package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class NectarShooterCommand {
    ShooterSubsystem shooterSubsystem;
    Gamepad gamepad;

    public NectarShooterCommand(ShooterSubsystem shooterSubsystem, Gamepad gamepad){
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Gamepad gamepad){
        if (gamepad.dpad_down){
            shooterSubsystem.setNectarShooterPower(.67);
        }
        else if(gamepad.dpad_up){
            shooterSubsystem.setNectarShooterPower(.50);
        }
        else{
            shooterSubsystem.setNectarShooterPower(0);
        }
    }

    public void shutdown(){
        shooterSubsystem.NectarShutdown();
    }
}
