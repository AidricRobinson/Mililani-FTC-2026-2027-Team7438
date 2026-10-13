package org.firstinspires.ftc.teamcode.Commands.Test;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Localization;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class TestFlywheelDistance {
    private ShooterSubsystem shooterSubsystem;
    private Gamepad gamepad;
    private PIDController nectarPID;
    private PIDController pollenPID;
    private Telemetry telemetry;
    private Limelight3A limelight;
    private LLResult result;
    private double x;
    private double min;
    private double targetRotation;
    private double distanceT;
    private double distanceB;

    public TestFlywheelDistance(ShooterSubsystem shooterSubsystem, Limelight3A limelight, Gamepad gamepad, Telemetry telemetry) {
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
        this.limelight = limelight;
        this.telemetry = telemetry;
        nectarPID = Constants.PayloadConstants.nectarPID;
        pollenPID = Constants.PayloadConstants.pollenPID;
    }
    public void operate(String alliance, double dt) {
        if (gamepad.dpad_up) {
            shooterSubsystem.increaseNectarRPM(200);
        }
        if(gamepad.dpad_down) {
            shooterSubsystem.decreaseNectarRPM(50);
        }
        if(gamepad.dpad_right) {
            shooterSubsystem.setTestNectarRPM(2000);
        }
        if (gamepad.y) {
            shooterSubsystem.increasePollenRPM(200);
        }
        if (gamepad.a) {
            shooterSubsystem.decreasePollenRPM(50);
        }
        if (gamepad.b) {
            shooterSubsystem.setTestPollenRPM(2000);
        }
        if (gamepad.dpad_left) {
            nectarPID.createSetPoint(shooterSubsystem.getTestNectarRPM());
            nectarPID.setProcessVariable(shooterSubsystem.getNectarShooterRPM());
            shooterSubsystem.setNectarShooterPower(nectarPID.getOutput(dt));
        }
        else {
            shooterSubsystem.setNectarShooterPower(0);
            nectarPID.resetIntegral();
        }
        if (gamepad.x) {
            pollenPID.createSetPoint(shooterSubsystem.getTestPollenRPM());
            pollenPID.setProcessVariable(shooterSubsystem.getPollenShooterRPM());
            shooterSubsystem.setPollenShooterPower(pollenPID.getOutput(dt));
        }
        else {
            shooterSubsystem.setPollenShooterPower(0);
            pollenPID.resetIntegral();
        }
        result = limelight.getLatestResult();
        double ta = 0;
        if (result != null && result.isValid()) {
            ta = result.getTa();
        }
        if (alliance.equals("RED")) {
            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);
            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
            min = Math.min(distanceT, distanceB);
            x = min;
            if (min == distanceT) {
//                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);
            }
            if (min == distanceB) {
//                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
            }

        }
        if (alliance.equals("BLUE")) {
            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
            min = Math.min(distanceT, distanceB);
            x = min;
            if (min == distanceT) {
//                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
            }
            if (min == distanceB) {
//                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
            }

        }

        telemetry.addData("Pollen RPM: ", shooterSubsystem.getTestPollenRPM());
        telemetry.addData("Nectar RPM: ", shooterSubsystem.getTestNectarRPM());
        telemetry.addData("limelight ta: ", ta);
        telemetry.addData("Estimated distance: ", x);

        telemetry.update();
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        pollenPID.resetIntegral();
        nectarPID.resetIntegral();
    }
}
