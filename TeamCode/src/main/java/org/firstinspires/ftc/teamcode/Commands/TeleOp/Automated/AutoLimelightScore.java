package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoLimelightScore {
    private ShooterSubsystem shooterSubsystem;
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private StorageSubsystem storageSubsystem;
    private Limelight3A limelight;
    private SetNectarRPM setNectarRPM;
    private SetPollenRPM setPollenRPM;

    private LLResult result;
    double tx;
    double x;
    double kP = 0.7;
    double kFF = 0.05;
    double output;

    public AutoLimelightScore(ShooterSubsystem shooterSubsystem, StorageSubsystem storageSubsystem, Limelight3A limelight, MecanumDriveSubsystem mecanumDriveSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.limelight = limelight;

        setNectarRPM = new SetNectarRPM(shooterSubsystem);
        setPollenRPM = new SetPollenRPM(shooterSubsystem);
        tx = 0;
    }
    public void operate(double dt) {
        result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            tx = result.getTx();
            x = result.getTa();
        }
        else {
            tx = 20;
            x = 0;
        }

        if (shooterSubsystem.getPollenToggle()) {
            setPollenRPM.operate(Constants.PinpointConstants.kLimePA * Math.pow(x, 2)
                    + Constants.PinpointConstants.kLimePB * x
                    + Constants.PinpointConstants.kLimePC, dt);
        }
        if (shooterSubsystem.getNectarToggle()) {
            setNectarRPM.operate(Constants.PinpointConstants.kLimeNA * Math.pow(x, 2)
                    + Constants.PinpointConstants.kLimeNB * x
                    + Constants.PinpointConstants.kLimeNC, dt);
        }
        boolean aligned = Math.abs(tx) < 0.5;

        boolean nectarReady =
                !shooterSubsystem.getNectarToggle()
                        || Math.abs(setNectarRPM.getError()) < 100;

        boolean pollenReady =
                !shooterSubsystem.getPollenToggle()
                        || Math.abs(setPollenRPM.getError()) < 100;

        if (!aligned) {
            output = kP * tx + Math.copySign(kFF, tx);

            // Verify the motor signs on your robot.
            mecanumDriveSubsystem.setLeftFrontPower(output);
            mecanumDriveSubsystem.setLeftBackPower(output);
            mecanumDriveSubsystem.setRightFrontPower(-output);
            mecanumDriveSubsystem.setRightBackPower(-output);

            mecanumDriveSubsystem.setBrakePosition(0);

            storageSubsystem.shutdown();
        } else {
            mecanumDriveSubsystem.shutdown();
            storageSubsystem.setSpindexerPower(1);
            if (nectarReady && pollenReady) {
                mecanumDriveSubsystem.setBrakePosition(1);

                storageSubsystem.setStoragePower(0.75);
            } else {
                storageSubsystem.shutdown();
                mecanumDriveSubsystem.setBrakePosition(1);
            }
        }
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
        mecanumDriveSubsystem.shutdown();
        setNectarRPM.shutdown();
        setPollenRPM.shutdown();
    }
}
