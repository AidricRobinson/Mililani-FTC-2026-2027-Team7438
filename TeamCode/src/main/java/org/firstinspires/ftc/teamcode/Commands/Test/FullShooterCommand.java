package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class FullShooterCommand {
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;

    public FullShooterCommand(ShooterSubsystem shooterSubsystem, Gamepad gamepad){
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
        shooterSubsystem.setTestPower(0.5);
    }

    public void operate(Gamepad gamepad){
        if(gamepad.y) {
            shooterSubsystem.increasePower(0.1);
        }
        if (gamepad.a) {
            shooterSubsystem.decreasePower(0.05);
        }
        if (gamepad.b){
            shooterSubsystem.setTestPower(0.5);
        }
        if (gamepad.x) {
            shooterSubsystem.setPower(shooterSubsystem.getTestPower());
        }
        else {
            shooterSubsystem.setPower(0);
        }

    }

    public void shutdown(){
        shooterSubsystem.shutdown();
    }
}
