package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Localization;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class TestAutoAlign {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private Gamepad gamepad;

    private double targetRotation = 0;
    private double error;


    public TestAutoAlign(MecanumDriveSubsystem mecanumDriveSubsystem, Gamepad gamepad) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.gamepad = gamepad;
//        kP = .005; // CALCULATED REALISTIC MAX KP (MAX ERROR 180)
//        kFF = .05;
    }

    public void operate(String alliance, GoBildaPinpointDriver pinpoint, double kP, double kFF) {
        double distanceT;
        double distanceB;
        double min;
        double output;


        if (gamepad.left_bumper && alliance.equals(Constants.PinpointConstants.redAlliance)) {
            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);
            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
            min = Math.min(distanceT, distanceB);
            if (min == distanceT) {
                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);

            }
            if (min == distanceB) {
                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
            }

        }
        if (gamepad.left_bumper && alliance.equals(Constants.PinpointConstants.blueAlliance)) {
            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
            min = Math.min(distanceT, distanceB);
            if (min == distanceT) {
                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
            }
            if (min == distanceB) {
                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
            }

        }

//        targetRotation = Localization.getTargetRotation(72, 72, pinpoint);

        error = Localization.getAngleError(targetRotation, pinpoint.getHeading(AngleUnit.DEGREES));

        if (gamepad.left_bumper) {
            if (Math.abs(error) > 0.5) {
                error = Localization.getAngleError(targetRotation, pinpoint.getHeading(AngleUnit.DEGREES));
                output = kP * error + Math.copySign(kFF, error);
                mecanumDriveSubsystem.setRightFrontPower(-output);
                mecanumDriveSubsystem.setRightBackPower(-output);
                mecanumDriveSubsystem.setLeftFrontPower(+output);
                mecanumDriveSubsystem.setLeftBackPower(+output);
            }
            else {
                mecanumDriveSubsystem.shutdown();
            }

        }
        if (gamepad.leftBumperWasReleased()) {
            mecanumDriveSubsystem.shutdown();
        }

    }

    public void shutdown() {
        mecanumDriveSubsystem.shutdown();
    }



}
