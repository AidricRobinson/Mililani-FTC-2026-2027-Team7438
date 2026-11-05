package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Commands.Autonomous.AutoMoveLeft;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

@Autonomous(name="Autonomous Move Left", group = "Linear OpMode")
public class AutonomousLeaveLeft extends LinearOpMode {
    private MecanumDriveSubsystem mecanumDriveSubsystem;

    private AutoMoveLeft autoMoveLeft;



    @Override
    public void runOpMode() {
        mecanumDriveSubsystem = new MecanumDriveSubsystem(this.hardwareMap, this);

        autoMoveLeft = new AutoMoveLeft(mecanumDriveSubsystem, this);

        waitForStart();
        autoMoveLeft.operate();

        mecanumDriveSubsystem.shutdown();
    }
}
