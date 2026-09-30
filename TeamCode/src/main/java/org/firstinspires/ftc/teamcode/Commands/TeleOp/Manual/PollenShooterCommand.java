package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class PollenShooterCommand {
    ShooterSubsystem shooterSubsystem;
    Gamepad gamepad;

    public PollenShooterCommand(ShooterSubsystem shooterSubsystem, Gamepad gamepad){
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
    }

    public void operate(Gamepad gamepad){
        if (gamepad.dpad_left){
            shooterSubsystem.setPollenShooterPower(.67);
        }
        else if (gamepad.dpad_right){
            shooterSubsystem.setPollenShooterPower(.5);
        }
        else{
            shooterSubsystem.setPollenShooterPower(0);
        }
    }

    public void shutdown(){
        shooterSubsystem.PollenShutdown();
    }
}
