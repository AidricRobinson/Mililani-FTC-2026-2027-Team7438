package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class SetNectarRPM {
    private ShooterSubsystem shooterSubsystem;
    private PIDController pidController;
    public SetNectarRPM(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        pidController = Constants.PayloadConstants.nectarPID;
    }
    public void operate(double rpm) {
        pidController.createSetPoint(rpm);
        pidController.setProcessVariable(shooterSubsystem.getNectarShooterRPM());
        shooterSubsystem.setNectarShooterPower(pidController.getOutput());
    }
    public void shutdown() {
        pidController.resetIntegral();
        shooterSubsystem.shutdown();
    }
    public double getError() {
        return pidController.getError();
    }
}
