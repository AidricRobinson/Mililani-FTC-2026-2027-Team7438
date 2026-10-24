package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoShooterCommand {

    private final ShooterSubsystem shooterSubsystem;
    private final StorageSubsystem storageSubsystem;
    private final PIDController pidController = Constants.PayloadConstants.shooterPID;
    private final ElapsedTime timer = new ElapsedTime();

    private double currentTime = 0;
    private double lastTime = 0;
    private double durationSeconds;

    public AutoShooterCommand(ShooterSubsystem shooterSubsystem, StorageSubsystem storageSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
    }



    public void initialize(double targetRPM, double durationSeconds) {
        pidController.createSetPoint(targetRPM);
        this.durationSeconds = durationSeconds;
        timer.reset();
    }

    /** Call every loop. Returns true when finished (or if not running). */
    public boolean update() {
        if (timer.seconds() < durationSeconds) {
            currentTime = timer.seconds();
            pidController.setProcessVariable(shooterSubsystem.getLeftVelocity());
            shooterSubsystem.setPower(pidController.getOutput(currentTime - lastTime));
            storageSubsystem.setStoragePower(0.5);
            storageSubsystem.setSpindexerPower(1);
            lastTime = currentTime;
            return true;
        }
        return false;

    }

    public void shutdown() {

        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
    }
}