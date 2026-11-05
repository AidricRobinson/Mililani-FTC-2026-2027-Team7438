package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Localization;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class AutoTurn {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private PinpointLocalizer pinpointLocalizer;
    private LinearOpMode linearOpMode;
    private final double kP = .02125;
    private final double kFF = .05;
    private double error = 180;
    public AutoTurn(MecanumDriveSubsystem mecanumDriveSubsystem, PinpointLocalizer pinpointLocalizer, LinearOpMode linearOpMode) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.linearOpMode = linearOpMode;
        this.pinpointLocalizer = pinpointLocalizer;
    }
    public void operate(double targetRotation) {
        error = Localization.getAngleError(targetRotation, pinpointLocalizer.getHeading());
        while (error >= .5 && linearOpMode.opModeIsActive()) {
            double output = 0;
            pinpointLocalizer.update();
            error = Localization.getAngleError(targetRotation, pinpointLocalizer.getHeading());
            output = kP * error * Math.copySign(kFF, error);

            mecanumDriveSubsystem.setRightFrontPower(-output);
            mecanumDriveSubsystem.setRightBackPower(-output);
            mecanumDriveSubsystem.setLeftFrontPower(+output);
            mecanumDriveSubsystem.setLeftBackPower(+output);
        }
        mecanumDriveSubsystem.shutdown();
    }
    public void shutdown() {
        mecanumDriveSubsystem.shutdown();
    }
}
