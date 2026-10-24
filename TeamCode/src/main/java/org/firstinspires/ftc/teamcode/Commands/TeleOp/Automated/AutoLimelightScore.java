package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoLimelightScore {
    private ShooterSubsystem shooterSubsystem;
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private StorageSubsystem storageSubsystem;
    private Limelight3A limelight;
    private PIDController pidController = Constants.PayloadConstants.shooterPID;
//    private SetNectarRPM setNectarRPM;
//    private SetPollenRPM setPollenRPM;

    private LLResult result;
    double tx;
    double x;
    double kP = 0.4;
    double kFF = 0.05;
    double output;

    public AutoLimelightScore(ShooterSubsystem shooterSubsystem, StorageSubsystem storageSubsystem, Limelight3A limelight, MecanumDriveSubsystem mecanumDriveSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.limelight = limelight;

//        setNectarRPM = new SetNectarRPM(shooterSubsystem);
//        setPollenRPM = new SetPollenRPM(shooterSubsystem);
        tx = 0;
    }
    public void operate(double dt) {
        result = limelight.getLatestResult();
        boolean hasTarget = result != null && result.isValid() && result.getStaleness() < 100; // ms
        x = 0;
        if (hasTarget) {
            tx = result.getTx();
            x = result.getTa();
        } else {
            // No fresh target: hold still, don't spin or feed
            mecanumDriveSubsystem.shutdown();
            storageSubsystem.shutdown();

            return;
        }



        boolean aligned = Math.abs(tx) < 0.5;
        pidController.createSetPoint(Constants.PinpointConstants.kLimeA * Math.pow(x, 2)
        + Constants.PinpointConstants.kLimeB * x
        + Constants.PinpointConstants.kLimeC);
        pidController.setProcessVariable(shooterSubsystem.getRPM(dt));
        shooterSubsystem.requestPower(pidController.getOutput(dt));


        boolean shooterReady = Math.abs(pidController.getError()) < 100;



        if (!aligned) {
            output = Math.max(-0.4, Math.min(0.4, kP * tx + Math.copySign(kFF, tx)));

            // Verify the motor signs on your robot.
            mecanumDriveSubsystem.setLeftFrontPower(output);
            mecanumDriveSubsystem.setLeftBackPower(output);
            mecanumDriveSubsystem.setRightFrontPower(-output);
            mecanumDriveSubsystem.setRightBackPower(-output);

            mecanumDriveSubsystem.requestBrake(0);
            // no storage request = storage stays off
        } else {
            mecanumDriveSubsystem.shutdown();
            storageSubsystem.requestSpindexerPower(1);
            mecanumDriveSubsystem.requestBrake(1);
            if (shooterReady) {
                storageSubsystem.requestStoragePower(0.75);
                storageSubsystem.requestSpindexerPower(1);
            }
            // not ready yet: no storage request = feed stays off
        }
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
        mecanumDriveSubsystem.shutdown();
        pidController.resetIntegral();
    }
}
