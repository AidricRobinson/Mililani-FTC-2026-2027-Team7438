//package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;
//
//import com.qualcomm.robotcore.hardware.Gamepad;
//
//import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
//
//public class ToggleFlywheels {
//    private ShooterSubsystem shooterSubsystem;
//    private Gamepad gamepad;
//
//    public ToggleFlywheels(ShooterSubsystem shooterSubsystem, Gamepad gamepad) {
//        this.shooterSubsystem = shooterSubsystem;
//        this.gamepad = gamepad;
//    }
//    public void operate() {
//        if (gamepad.dpad_down) {
//            shooterSubsystem.setNectarToggle(true);
//            shooterSubsystem.setPollenToggle(true);
//        }
//        if (gamepad.dpad_left) {
//            shooterSubsystem.setPollenToggle(true);
//            shooterSubsystem.setNectarToggle(false);
//        }
//        if (gamepad.dpad_right) {
//            shooterSubsystem.setPollenToggle(false);
//            shooterSubsystem.setNectarToggle(true);
//        }
//    }
//    public void shutdown() {
//        shooterSubsystem.setNectarToggle(true);
//        shooterSubsystem.setPollenToggle(true);
//    }
//}