package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutonomousShoot {
    private LinearOpMode linearOpMode;
    private ShooterSubsystem shooterSubsystem;
    private StorageSubsystem storageSubsystem;
    private ElapsedTime elapsedTime;

    public AutonomousShoot(LinearOpMode linearOpMode,
                           ShooterSubsystem shooterSubsystem,
                           StorageSubsystem storageSubsystem,
                           ElapsedTime elapsedTime) {
        this.linearOpMode = linearOpMode;
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.elapsedTime = elapsedTime;
    }
    public void operate() {
        double startTime = elapsedTime.seconds();

        while (linearOpMode.opModeIsActive() && elapsedTime.seconds() <= startTime + 8) {
            shooterSubsystem.setPower(.5);

            if (elapsedTime.seconds() >= startTime + 2) {
                storageSubsystem.setSpindexerPower(1);
                storageSubsystem.setStoragePower(.5);
            }
        }
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
    }
}
