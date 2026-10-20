package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constants;


public class MecanumDriveSubsystem {
    //Make 4 motors (the four corne`rs)
//    BHI260AP imu;
    DcMotorEx leftFront;
    DcMotorEx leftBack;
    DcMotorEx rightFront;
    DcMotorEx rightBack;

    Servo brake;



//    DcMotorEx xThroughbore;
//    DcMotorEx yThroughbore;
    double heading;

    GoBildaPinpointDriver pinpoint;



    private boolean slowModeOn;
   public MecanumDriveSubsystem(HardwareMap hardwareMap, OpMode opMode, GoBildaPinpointDriver pinpoint){
       this.pinpoint = pinpoint;
//       brake = hardwareMap.get(Servo.class, "brake");



       leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
       leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
       rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
       rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");





//       xThroughbore = hardwareMap.get(DcMotorEx.class, "xThroughbore");
//       yThroughbore = hardwareMap.get(DcMotorEx.class, "yThroughbore");



       leftFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
       leftBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
       rightFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
       rightBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

       leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       leftFront.setDirection(DcMotorEx.Direction.FORWARD);
       leftBack.setDirection(DcMotorEx.Direction.REVERSE);
       rightFront.setDirection(DcMotorEx.Direction.FORWARD);
       rightBack.setDirection(DcMotorEx.Direction.REVERSE);


   }
   public void operate(Gamepad gamepad, Telemetry telemetry, String alliance) {

        telemetry.addData("Pose X: ", pinpoint.getPosX(DistanceUnit.INCH));
        telemetry.addData("Pose Y: ", pinpoint.getPosY(DistanceUnit.INCH));
        telemetry.addData("Rotation: ", pinpoint.getHeading(AngleUnit.DEGREES));

       telemetry.update();
       heading = pinpoint.getHeading(AngleUnit.RADIANS);
       if (alliance.equals(Constants.PinpointConstants.blueAlliance)) {
           heading = Math.toDegrees(heading);
           heading += 180;
           if (heading >= 180) {
               heading -= 360;
           }
           heading = Math.toRadians(heading);
       }


       double y = gamepad.left_stick_y;
       double x = gamepad.left_stick_x;
       double rx = gamepad.right_stick_x;


       double botHeading = heading;
       double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
       double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);
       double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);




//         slowmode - disabled for showcasing

//        if (gamepad.right_trigger > 0.2) {
//
//            leftFront.setPower((rotY + rotX - rx) / denominator * 0.5);
//            leftBack.setPower((rotY - rotX - rx) / denominator * 0.5);
//            rightFront.setPower((rotY - rotX + rx) / denominator * 0.5);
//            rightBack.setPower((rotY + rotX + rx)/ denominator * 0.5);
//        }

       //Field oriented version
       double lf = (rotY - rotX + rx) / denominator;
       double lb = (rotY + rotX + rx) / denominator;
       double rf = (rotY + rotX - rx) / denominator;
       double rb = (rotY - rotX - rx) / denominator;
       if (gamepad.right_trigger >= 0.2) {
           leftFront.setPower(lf * 0.4);
           leftBack.setPower(lb * 0.4);
           rightFront.setPower(rf * 0.4);
           rightBack.setPower(rb * 0.4);
       }
       else if (gamepad.left_bumper) {
           leftFront.setPower(lf * 0.75);
           leftBack.setPower(lb * 0.75);
           rightFront.setPower(rf * 0.75);
           rightBack.setPower(rb * 0.75);
       }
       else {
           leftFront.setPower(lf);
           leftBack.setPower(lb);
           rightFront.setPower(rf);
           rightBack.setPower(rb);
       }

       //Robot oriented - retired and is no longer needed

//            leftFront.setPower((y + x - rx) / denominator);
//            leftBack.setPower((y - x - rx) / denominator);
//            rightFront.setPower((y - x + rx) / denominator);
//            rightBack.setPower((y + x + rx)/ denominator);


   }

   public void setBrakePosition(int position) {
       brake.setPosition(position);
   }



    public void setLeftBackPower(double power){
       leftBack.setPower(power);
    }
    public void setRightBackPower(double power){
       rightBack.setPower(power);
    }
    public void setLeftFrontPower(double power){
        leftFront.setPower(power);
    }
    public void setRightFrontPower(double power){
        rightFront.setPower(power);
    }

    public double[] encoderReading () {
        double[] encoderReading = new double[4];
        encoderReading[0] = leftFront.getCurrentPosition();
        encoderReading[1] = leftBack.getCurrentPosition();
        encoderReading[2] = rightFront.getCurrentPosition();
        encoderReading[3] = rightBack.getCurrentPosition();

        return encoderReading;
    }



    public void shutdown() {
       leftFront.setPower(0);
       leftBack.setPower(0);
       rightFront.setPower(0);
       rightBack.setPower(0);
    }


    private double brakeRequest = 0;
    private boolean hasBrakeRequest = false;

    public void requestBrake(double position) {
        brakeRequest = position;
        hasBrakeRequest = true;
    }

    /** Call once at the END of loop(). Safe if the brake servo isn't installed. */
    public void updateBrake() {
        if (brake != null && hasBrakeRequest) {
            brake.setPosition(brakeRequest);
        }
        hasBrakeRequest = false;
    }
}
