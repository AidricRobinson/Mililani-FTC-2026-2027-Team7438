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

    public TestFlywheelDistance(ShooterSubsystem shooterSubsystem, Limelight3A limelight, Gamepad gamepad, Telemetry telemetry,
                                double nectarP, double nectarI, double nectarD, double nectarFF,
                                double pollenP, double pollenI, double pollenD, double pollenFF) {
        this.shooterSubsystem = shooterSubsystem;
        this.gamepad = gamepad;
        this.limelight = limelight;
        this.telemetry = telemetry;
        nectarPID = new PIDController(nectarP, nectarI, nectarD, nectarFF);
        pollenPID = new PIDController(pollenP, pollenI, pollenD, pollenFF);
    }
    public void operate(String alliance, double dt, double pollenRPM, double nectarRPM) {
        if (gamepad.x) {
            pollenPID.createSetPoint(pollenRPM);
            pollenPID.setProcessVariable(shooterSubsystem.getPollenShooterRPM());
            shooterSubsystem.setPollenShooterPower(pollenPID.getOutput(dt));
        }
        else {
            shooterSubsystem.setPollenShooterPower(0);
            pollenPID.resetIntegral();
        }
        if (gamepad.b) {
            nectarPID.createSetPoint(nectarRPM);
            nectarPID.setProcessVariable(shooterSubsystem.getNectarShooterRPM());
            shooterSubsystem.setNectarShooterPower(nectarPID.getOutput(dt));
        }
        else {
            shooterSubsystem.setNectarShooterPower(0);
            nectarPID.resetIntegral();
        }
        result = limelight.getLatestResult();
        double ta = 0;
        if (result != null && result.isValid()) {
            ta = result.getTa();
        }
        x = 0;
        if (alliance.equals("RED")) {
            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);
            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
            min = Math.min(distanceT, distanceB);
            x = min;
        }
        if (alliance.equals("BLUE")) {
            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
            min = Math.min(distanceT, distanceB);
            x = min;
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
