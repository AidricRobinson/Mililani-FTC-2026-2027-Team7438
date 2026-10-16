package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class SetPollenRPM {
    private ShooterSubsystem shooterSubsystem;
    private PIDController pidController;

    public SetPollenRPM(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        pidController = Constants.PayloadConstants.pollenPID;
    }
    public void operate(double rpm, double dt) {
        pidController.createSetPoint(rpm);
        pidController.setProcessVariable(shooterSubsystem.getPollenShooterRPM());
        shooterSubsystem.setPollenShooterPower(pidController.getOutput(dt));
    }
    public void shutdown() {
        pidController.resetIntegral();
        shooterSubsystem.shutdown();
    }
    public double getError() {
        return pidController.getError();
    }
}
