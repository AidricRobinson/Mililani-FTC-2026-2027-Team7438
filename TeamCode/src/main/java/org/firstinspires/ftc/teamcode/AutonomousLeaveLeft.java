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
    private ElapsedTime elapsedTime;



    @Override
    public void runOpMode() {
        elapsedTime = new ElapsedTime();
        mecanumDriveSubsystem = new MecanumDriveSubsystem(this.hardwareMap, this);

        autoMoveLeft = new AutoMoveLeft(mecanumDriveSubsystem, this, elapsedTime);

        waitForStart();
        elapsedTime.startTime();
        autoMoveLeft.operate(30);

        mecanumDriveSubsystem.shutdown();
    }
}
