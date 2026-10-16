package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoShooterCommand {
    private enum Mode { IDLE, POLLEN, NECTAR }

    private final ShooterSubsystem shooterSubsystem;
    private final StorageSubsystem storageSubsystem;
    private final ElapsedTime timer = new ElapsedTime();
    private Mode mode = Mode.IDLE;
    private double power;
    private double durationSeconds;

    public AutoShooterCommand(ShooterSubsystem shooterSubsystem, StorageSubsystem storageSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
    }

    public void startPollen(double power, double durationSeconds) { begin(Mode.POLLEN, power, durationSeconds); }
    public void startNectar(double power, double durationSeconds) { begin(Mode.NECTAR, power, durationSeconds); }

    private void begin(Mode mode, double power, double durationSeconds) {
        this.mode = mode;
        this.power = power;
        this.durationSeconds = durationSeconds;
        timer.reset();
    }

    /** Call every loop. Returns true when finished (or if not running). */
    public boolean update() {
        if (mode == Mode.IDLE) return true;
        if (timer.seconds() >= durationSeconds) {
            shutdown();
            return true;
        }
        if (mode == Mode.POLLEN) {
            shooterSubsystem.setPollenShooterPower(power);
            storageSubsystem.setStoragePower(.76);
        } else {
            shooterSubsystem.setNectarShooterPower(power);
            storageSubsystem.setStoragePower(.767);
        }
        storageSubsystem.setSpindexerPower(1);
        return false;
    }

    public void shutdown() {
        mode = Mode.IDLE;
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
    }
}