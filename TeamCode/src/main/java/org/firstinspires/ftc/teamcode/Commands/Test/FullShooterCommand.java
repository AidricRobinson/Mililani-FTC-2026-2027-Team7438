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

    public void operate(){
//        if(gamepad.y) {
//            shooterSubsystem.increasePower(0.1);
//        }
//        if (gamepad.a) {
//            shooterSubsystem.decreasePower(0.05);
//        }
//        if (gamepad.b){
//            shooterSubsystem.setTestPower(0.5);
//        }
//        if (gamepad.x) {
//            shooterSubsystem.setPower(shooterSubsystem.getTestPower());
//        }
//        else {
//            shooterSubsystem.setPower(0);
//        }
//        if (gamepad.dpad_left) {
//            shooterSubsystem.setLeftFlywheelPower(1);
//        }
//        else if (gamepad.dpad_right) {
//            shooterSubsystem.setRightFlywheel(1);
//        }
//        else if (gamepad.dpad_up) {
//            shooterSubsystem.setPower(1);
//        }
//        else {
//            shutdown();
//        }
        if (gamepad.right_bumper) {
            shooterSubsystem.setPower(.5);
        }
        else {
            shooterSubsystem.shutdown();
        }

    }

    public void shutdown(){
        shooterSubsystem.shutdown();
    }
}
