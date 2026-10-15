package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoIntakeCommand {
    private final IntakeSubsystem intakeSubsystem;
    private final StorageSubsystem storageSubsystem;
    private final ElapsedTime timer = new ElapsedTime();
    private double power;
    private double durationSeconds;
    private boolean running = false;

    public AutoIntakeCommand(IntakeSubsystem intakeSubsystem, StorageSubsystem storageSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.storageSubsystem = storageSubsystem;
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
        storageSubsystem.setStoragePower(.2);
        return false;
    }

    public void shutdown() {
        running = false;
        intakeSubsystem.shutdown();
        storageSubsystem.shutdown();
    }
}