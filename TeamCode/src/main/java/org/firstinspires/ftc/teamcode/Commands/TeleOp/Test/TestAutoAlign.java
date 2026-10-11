package org.firstinspires.ftc.teamcode.Commands.TeleOp.Test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Localization;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class TestAutoAlign {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private Gamepad gamepad;

    private double targetRotation = 0;
    private double error;
    private double kFF;
    private double kP;

    public TestAutoAlign(MecanumDriveSubsystem mecanumDriveSubsystem, Gamepad gamepad) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.gamepad = gamepad;
        kP = .005; // CALCULATED REALISTIC MAX KP (MAX ERROR 180)
        kFF = .05;
    }

    public void operate(String alliance) {
        double distanceT;
        double distanceB;
        double min;
        double output;


//        if (gamepad.left_bumper && alliance.equals("RED")) {
//            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);
//            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
//            min = Math.min(distanceT, distanceB);
//            if (min == distanceT) {
//                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);
//
//            }
//            if (min == distanceB) {
//                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
//            }
//
//        }
//        if (gamepad.left_bumper && alliance.equals("BLUE")) {
//            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
//            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
//            min = Math.min(distanceT, distanceB);
//            if (min == distanceT) {
//                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
//            }
//            if (min == distanceB) {
//                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
//            }
//
//        }

        targetRotation = Localization.getTargetRotation(72, 72);

        error = Localization.getAngleError(targetRotation, Localization.getPoseRotation());

        while (gamepad.left_bumper && Math.abs(error) > 0.5) {
            error = Localization.getAngleError(targetRotation, Localization.getPoseRotation());
            output = kP * error + Math.copySign(kFF, error);
            mecanumDriveSubsystem.setRightFrontPower(output);
            mecanumDriveSubsystem.setRightBackPower(output);
            mecanumDriveSubsystem.setLeftFrontPower(-output);
            mecanumDriveSubsystem.setLeftBackPower(-output);
        }

    }

    public void shutdown() {
        mecanumDriveSubsystem.shutdown();
    }



}
