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
    private PIDController pidController;
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
        pidController = Constants.PayloadConstants.shooterPID;
        shooterSubsystem.setTestRPM(2000);
    }
    public void operate(String alliance, double dt) {
        if (gamepad.y) {
            shooterSubsystem.increaseRPM(200);
        }
        if (gamepad.a) {
            shooterSubsystem.decreaseRPM(50);
        }
        if (gamepad.b) {
            shooterSubsystem.setTestRPM(2000);
        }
        if (gamepad.x) {
            pidController.createSetPoint(shooterSubsystem.getTestRPM());
            pidController.setProcessVariable(shooterSubsystem.getRPM(dt));
            shooterSubsystem.setPower(pidController.getOutput(dt));
        }
        else {
            shooterSubsystem.shutdown();
            pidController.resetIntegral();
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

        telemetry.addData("Pollen RPM: ", shooterSubsystem.getTestRPM());
        telemetry.addData("limelight ta: ", ta);
        telemetry.addData("Estimated distance: ", x);

        telemetry.update();
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        pidController.resetIntegral();
    }
}
