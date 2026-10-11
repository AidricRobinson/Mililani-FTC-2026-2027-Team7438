package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Localization;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoLocalizationScore {
    private ShooterSubsystem shooterSubsystem;
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private SetNectarRPM setNectarRPM;
    private SetPollenRPM setPollenRPM;
    private StorageSubsystem storageSubsystem;
    private double error;
    private double kFF;
    private double kP;
    private double output;
    private double targetRotation;
    private double x;
    private double distanceT;
    private double distanceB;
    private double min;
    public AutoLocalizationScore(ShooterSubsystem shooterSubsystem, MecanumDriveSubsystem mecanumDriveSubsystem, StorageSubsystem storageSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.storageSubsystem = storageSubsystem;
        setNectarRPM = new SetNectarRPM(shooterSubsystem);
        setPollenRPM = new SetPollenRPM(shooterSubsystem);

        kP = 0.005;
        kFF = 0.05;
        x = 0;
        targetRotation = 0;
    }
    public void operate(String alliance) {
        if (alliance.equals("RED")) {
            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);
            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
            min = Math.min(distanceT, distanceB);
            x = min;
            if (min == distanceT) {
                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kTopRedHive[0], Constants.PinpointConstants.kTopRedHive[1]);
            }
            if (min == distanceB) {
                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kBottomRedHive[0], Constants.PinpointConstants.kBottomRedHive[1]);
            }

        }
        if (alliance.equals("BLUE")) {
            distanceT = Localization.getDistance(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
            distanceB = Localization.getDistance(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
            min = Math.min(distanceT, distanceB);
            x = min;
            if (min == distanceT) {
                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kTopBlueHive[0], Constants.PinpointConstants.kTopBlueHive[1]);
            }
            if (min == distanceB) {
                targetRotation = Localization.getTargetRotation(Constants.PinpointConstants.kBottomBlueHive[0], Constants.PinpointConstants.kBottomBlueHive[1]);
            }

        }
        if (shooterSubsystem.getNectarToggle()) {
            setNectarRPM.operate(Constants.PinpointConstants.kPoseNA * Math.pow(x, 2)
                    + Constants.PinpointConstants.kPoseNB * x
                    + Constants.PinpointConstants.kPoseNC);
        }

        if (shooterSubsystem.getPollenToggle()) {
            setPollenRPM.operate(Constants.PinpointConstants.kPosePA * Math.pow(x, 2)
                    + Constants.PinpointConstants.kPosePB * x
                    + Constants.PinpointConstants.kPosePC);
        }
        error = Localization.getAngleError(targetRotation, Localization.getPoseRotation());
        if (Math.abs(error) >= 0.5) {
            error = Localization.getAngleError(targetRotation, Localization.getPoseRotation());
            output = kP * error + Math.copySign(kFF, error);
            mecanumDriveSubsystem.setRightFrontPower(output);
            mecanumDriveSubsystem.setRightBackPower(output);
            mecanumDriveSubsystem.setLeftFrontPower(-output);
            mecanumDriveSubsystem.setLeftBackPower(-output);
            mecanumDriveSubsystem.setBrakePosition(0);
            storageSubsystem.shutdown();
        } else {
            if(!(shooterSubsystem.getNectarToggle() && Math.abs(setNectarRPM.getError()) >= 100)
                    && !(shooterSubsystem.getPollenToggle() && Math.abs(setPollenRPM.getError()) >= 100)) {
                mecanumDriveSubsystem.shutdown();
                mecanumDriveSubsystem.setBrakePosition(1);
                storageSubsystem.setSpindexerPower(1);
                storageSubsystem.setStoragePower(0.5);
            }
        }
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        setPollenRPM.shutdown();
        setNectarRPM.shutdown();
        storageSubsystem.shutdown();
        mecanumDriveSubsystem.shutdown();
    }
}
