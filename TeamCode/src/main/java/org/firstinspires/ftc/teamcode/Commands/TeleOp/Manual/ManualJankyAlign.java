package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Localization;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class ManualJankyAlign {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private PinpointLocalizer pinpointLocalizer;
    private Gamepad gamepad;


    private final double kP = .02125;
    private final double kFF = .05;
    private double setPoint;
    private double error = 180;

    public ManualJankyAlign (MecanumDriveSubsystem mecanumDriveSubsystem, PinpointLocalizer pinpointLocalizer, Gamepad gamepad) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.pinpointLocalizer = pinpointLocalizer;
        this.gamepad = gamepad;


    }
    public void operate(String alliance) {
//        if (gamepad.left_bumper && alliance.equals(Constants.PinpointConstants.redAlliance)
//        || gamepad.right_bumper && alliance.equals(Constants.PinpointConstants.blueAlliance)) {
//            setPoint = -90;
//            error = Localization.getAngleError(setPoint, pinpointLocalizer.getHeading());
//            double output = 0;
//            pinpointLocalizer.update();
//            output = kP * error * Math.copySign(kFF, error);
//
//            mecanumDriveSubsystem.setRightFrontPower(-output);
//            mecanumDriveSubsystem.setRightBackPower(-output);
//            mecanumDriveSubsystem.setLeftFrontPower(+output);
//            mecanumDriveSubsystem.setLeftBackPower(+output);
//        }
//        else if (gamepad.right_bumper && alliance.equals(Constants.PinpointConstants.redAlliance)
//        || gamepad.left_bumper && alliance.equals(Constants.PinpointConstants.blueAlliance)) {
//            setPoint = 90;
//            error = Localization.getAngleError(setPoint, pinpointLocalizer.getHeading());
//            double output = 0;
//            pinpointLocalizer.update();
//            output = kP * error * Math.copySign(kFF, error);
//
//            mecanumDriveSubsystem.setRightFrontPower(-output);
//            mecanumDriveSubsystem.setRightBackPower(-output);
//            mecanumDriveSubsystem.setLeftFrontPower(+output);
//            mecanumDriveSubsystem.setLeftBackPower(+output);
//        }
//        if (gamepad.leftBumperWasReleased() || gamepad.rightBumperWasReleased()) {
//            mecanumDriveSubsystem.shutdown();
//        }


    }
    public void shutdown() {
        mecanumDriveSubsystem.shutdown();
    }
}
