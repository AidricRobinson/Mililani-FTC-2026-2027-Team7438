package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class AutoMoveBackward {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private LinearOpMode linearOpMode;

    public AutoMoveBackward(MecanumDriveSubsystem mecanumDriveSubsystem, LinearOpMode linearOpMode) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.linearOpMode = linearOpMode;
    }

    public void operate() {
        while(linearOpMode.opModeIsActive()) {
            mecanumDriveSubsystem.setLeftFrontPower(-.25);
            mecanumDriveSubsystem.setLeftBackPower(-.25);
            mecanumDriveSubsystem.setRightFrontPower(-.25);
            mecanumDriveSubsystem.setRightBackPower(-.25);
        }
    }
    public void shutdown() {
        mecanumDriveSubsystem.shutdown();
    }
}
