package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.SpeedPIDController;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;


public class AutonomousShoot {
    private LinearOpMode linearOpMode;
    private ShooterSubsystem shooterSubsystem;
    private TransferSubsystem transferSubsystem;
    private ElapsedTime elapsedTime;
    private SpeedPIDController shooterPID;

    public AutonomousShoot(LinearOpMode linearOpMode,
                           ShooterSubsystem shooterSubsystem,
                           TransferSubsystem transferSubsystem,
                           ElapsedTime elapsedTime) {
        this.linearOpMode = linearOpMode;
        this.shooterSubsystem = shooterSubsystem;
        this.transferSubsystem = transferSubsystem;
        this.shooterPID = Constants.PIDConstants.kShooterPID;
        this.elapsedTime = elapsedTime;
    }
    public void operate(double shooterRPM) {
        double startTime = elapsedTime.seconds();
        shooterPID.createSetPoint(shooterRPM);
        while (linearOpMode.opModeIsActive() && elapsedTime.seconds() <= startTime + 8) {
            shooterPID.setProcessVariable(shooterSubsystem.getRPM());
            shooterSubsystem.setPower(shooterPID.getOutput());

            if (elapsedTime.seconds() >= startTime + 2) {
                transferSubsystem.setBottomHarvesterPower(1);
                transferSubsystem.setTopHarvesterPower(.5);
            }
        }
        shooterSubsystem.shutdown();
        transferSubsystem.shutdown();
    }
    public void shutdown() {
        shooterSubsystem.shutdown();
        transferSubsystem.shutdown();
    }
}
