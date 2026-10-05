package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Localization;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class AutoAlignCommand {
    MecanumDriveSubsystem mecanumDriveSubsystem;
    Gamepad gamepad;

    double targetRotation = 0;
    double error;
    double kFF;
    double kP;

    public AutoAlignCommand(MecanumDriveSubsystem mecanumDriveSubsystem, Gamepad gamepad) {
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


        if (gamepad.left_bumper && alliance.equals("RED")) {
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
        if (gamepad.left_bumper && alliance.equals("BLUE")) {
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
        error = getError(targetRotation, Localization.getPoseRotation());

        while (gamepad.left_bumper && Math.abs(error) > 0.5) {
            error = getError(targetRotation, Localization.getPoseRotation());
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

    public double getError(double T, double C) {
        return ((T - C + 180) % 360) - 180;
    }

}
