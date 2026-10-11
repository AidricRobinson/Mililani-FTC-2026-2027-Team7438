package org.firstinspires.ftc.teamcode.Commands.TeleOp.Test;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated.SetNectarRPM;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated.SetPollenRPM;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class TestLimelightShootCommand {
    private ShooterSubsystem shooterSubsystem;
    private StorageSubsystem storageSubsystem;
    private Limelight3A limelight;
    private Gamepad gamepad;
    private LLResult result;
    private SetPollenRPM setPollenRPM;
    private SetNectarRPM setNectarRPM;
    double x;

    public TestLimelightShootCommand(ShooterSubsystem shooterSubsystem,
                                     StorageSubsystem storageSubsystem,
                                     Limelight3A limelight,
                                     Gamepad gamepad) {
        this.shooterSubsystem = shooterSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.limelight = limelight;
        this.gamepad = gamepad;
        setNectarRPM = new SetNectarRPM(shooterSubsystem);
        setPollenRPM = new SetPollenRPM(shooterSubsystem);
        x = 0;
    }
    public void operate() {
        result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            x = result.getTa();
        }
        if (gamepad.right_bumper) {
            if (shooterSubsystem.getPollenToggle()) {
                setPollenRPM.operate(Constants.PinpointConstants.kLimePA * Math.pow(x, 2)
                        + Constants.PinpointConstants.kLimePB * x
                        + Constants.PinpointConstants.kLimePC);
            }
            if (shooterSubsystem.getNectarToggle()) {
                setNectarRPM.operate(Constants.PinpointConstants.kLimeNA * Math.pow(x, 2)
                        + Constants.PinpointConstants.kLimeNB * x
                        + Constants.PinpointConstants.kLimeNC);
            }
        }
    }


}
