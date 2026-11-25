package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;

public class AutoIntakeCommand {
    private final IntakeSubsystem intakeSubsystem;
    private final TransferSubsystem transferSubsystem;
    private final ElapsedTime timer = new ElapsedTime();
    private double power;
    private double durationSeconds;
    private boolean running = false;

    public AutoIntakeCommand(IntakeSubsystem intakeSubsystem, TransferSubsystem transferSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.transferSubsystem = transferSubsystem;
    }

    public void start(double power, double durationSeconds) {
        this.power = power;
        this.durationSeconds = durationSeconds;
        timer.reset();
        running = true;
    }

    /** Call every loop. Returns true when finished (or if not running). */
    public boolean update() {
        if (!running) return true;
        if (timer.seconds() >= durationSeconds) {
            shutdown();
            return true;
        }
        intakeSubsystem.setIntakePower(power);
        transferSubsystem.setBottomHarvesterPower(1);
//        if (!transferSubsystem.beamBroken()) {
            transferSubsystem.setTopHarvesterPower(0.5);
//        }
        return false;
    }

    public void shutdown() {
        running = false;
        intakeSubsystem.shutdown();
        transferSubsystem.shutdown();
    }
}