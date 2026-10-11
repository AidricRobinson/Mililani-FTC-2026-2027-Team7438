package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoScore {
    private Gamepad gamepad;
    private AutoLocalizationScore localizationScore;
    private AutoLimelightScore autoLimelightScore;
    private ShooterSubsystem shooterSubsystem;

    public AutoScore(MecanumDriveSubsystem mecanumDriveSubsystem,
                     StorageSubsystem storageSubsystem,
                     ShooterSubsystem shooterSubsystem,
                     Limelight3A limelight,
                     Gamepad gamepad) {
        this.gamepad = gamepad;
        this.shooterSubsystem = shooterSubsystem;
        autoLimelightScore = new AutoLimelightScore(shooterSubsystem,
                storageSubsystem,
                limelight,
                mecanumDriveSubsystem);


        localizationScore = new AutoLocalizationScore(shooterSubsystem,
                mecanumDriveSubsystem, storageSubsystem);
    }
    public void operate(String alliance) {
        double distanceT;
        double distanceB;
        double min;
        double output;
        double x = 0;


        if (gamepad.right_bumper) {
            if (shooterSubsystem.getManualToggle()) {
                autoLimelightScore.operate();
            }
            else {
                localizationScore.operate(alliance);
            }
        }
        if (gamepad.rightBumperWasReleased()) {
            autoLimelightScore.shutdown();
            localizationScore.shutdown();
        }

    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        autoLimelightScore.shutdown();
        localizationScore.shutdown();
    }
}
