package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Commands.Autonomous.AutoMoveLeft;
import org.firstinspires.ftc.teamcode.Commands.Autonomous.AutoMoveRight;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

@Autonomous(name="Autonomous Move Right", group = "Linear OpMode")
public class AutonomousLeaveRight extends LinearOpMode {
    private MecanumDriveSubsystem mecanumDriveSubsystem;

    private AutoMoveRight autoMoveRight;
    private ElapsedTime elapsedTime;



    
    @Override
    public void runOpMode() {
        elapsedTime = new ElapsedTime();
        mecanumDriveSubsystem = new MecanumDriveSubsystem(this.hardwareMap, this);

        autoMoveRight = new AutoMoveRight(mecanumDriveSubsystem, this, elapsedTime);

        waitForStart();

        autoMoveRight.operate(30);

        mecanumDriveSubsystem.shutdown();
    }
}
