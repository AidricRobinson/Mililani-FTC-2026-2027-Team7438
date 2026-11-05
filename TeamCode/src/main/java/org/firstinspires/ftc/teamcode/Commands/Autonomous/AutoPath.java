package org.firstinspires.ftc.teamcode.Commands.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PinpointLocalizer;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class AutoPath {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private LinearOpMode linearOpMode;
    private PinpointLocalizer pinpointLocalizer;



    /*
    double y = gamepad.left_stick_y;
   double x = gamepad.left_stick_x;
   double rx = gamepad.right_stick_x;


   double botHeading = heading;
   double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
   double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);
   double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
    leftFront.setPower((rotY + rotX - rx) / denominator * 0.5);
    leftBack.setPower((rotY - rotX - rx) / denominator * 0.5);
    rightFront.setPower((rotY - rotX + rx) / denominator * 0.5);
    rightBack.setPower((rotY + rotX + rx)/ denominator * 0.5);
     */
}
