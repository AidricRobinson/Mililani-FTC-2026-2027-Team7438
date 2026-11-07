package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class AutoMoveForward {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private LinearOpMode linearOpMode;
    private ElapsedTime elapsedTime;

    public AutoMoveForward(MecanumDriveSubsystem mecanumDriveSubsystem, LinearOpMode linearOpMode, ElapsedTime elapsedTime) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.linearOpMode = linearOpMode;
        this.elapsedTime = elapsedTime;
    }

    public void operate(double seconds) {
        double start = elapsedTime.seconds();
        while(linearOpMode.opModeIsActive() && elapsedTime.seconds() < start + seconds) {
            mecanumDriveSubsystem.setLeftFrontPower(.25);
            mecanumDriveSubsystem.setLeftBackPower(.25);
            mecanumDriveSubsystem.setRightFrontPower(.25);
            mecanumDriveSubsystem.setRightBackPower(.25);
        }
        mecanumDriveSubsystem.shutdown();
    }
    public void shutdown() {
        mecanumDriveSubsystem.shutdown();
    }
}
