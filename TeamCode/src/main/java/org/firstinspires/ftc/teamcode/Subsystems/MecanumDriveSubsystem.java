package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Path;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.onbotjava.handlers.file.NewFile;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Constants;


public class MecanumDriveSubsystem {
    //Make 4 motors (the four corne`rs)
//    BHI260AP imu;
    DcMotorEx leftFront;
    DcMotorEx leftBack;
    DcMotorEx rightFront;
    DcMotorEx rightBack;
    boolean toggle;


//    DcMotorEx xThroughbore;
//    DcMotorEx yThroughbore;
    double heading;

    GoBildaPinpointDriver pinpoint;



    private boolean slowModeOn;
   public MecanumDriveSubsystem(HardwareMap hardwareMap, OpMode opMode){
        pinpoint = opMode.hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(Constants.PinpointConstants.kEncoderResolution, DistanceUnit.MM);
        pinpoint.setOffsets(Constants.PinpointConstants.kPodXOffset,
                Constants.PinpointConstants.kPodYOffset,
                DistanceUnit.CM);

        pinpoint.setEncoderDirections(Constants.PinpointConstants.kPodXDirection,
                Constants.PinpointConstants.kPodYDirection);

        pinpoint.initialize();
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 12, 12, AngleUnit.DEGREES, 90));




       leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
       leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
       rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
       rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");



       toggle = false;

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
       rightFront.setDirection(DcMotorEx.Direction.REVERSE);
       rightBack.setDirection(DcMotorEx.Direction.REVERSE);

   }
    public void operate(Gamepad gamepad, Telemetry telemetry, String alliance) {

//        localization.getRobotPose();
//        telemetry.addData("Pose X: ", localization.getPoseX());
//        telemetry.addData("Pose Y: ", localization.getPoseY());
//        telemetry.addData("Rotation: ", localization.getPoseRotation());
        pinpoint.update();
        telemetry.update();
        heading = pinpoint.getHeading(AngleUnit.RADIANS);
        if (alliance.equals(Constants.PinpointConstants.blueAlliance)){
            heading = Math.toDegrees(heading);
            heading += 180;
            if (heading >= 180) {
                heading -= 360;
            }
            heading = Math.toRadians(heading);
        }


        double y =  gamepad.left_stick_y;
        double x = gamepad.left_stick_x;
        double rx = gamepad.right_stick_x;


        double rotX = x * Math.cos(heading) - y * Math.sin(heading);
        double rotY = x * Math.sin(heading) + y * Math.cos(heading);
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);



        if(gamepad.x && toggle){
            toggle = false;
        }
        else if(gamepad.x){
            toggle = true;
        }

//         slowmode - disabled for showcasing

//        if (gamepad.right_trigger > 0.2) {
//
//            leftFront.setPower((rotY + rotX - rx) / denominator * 0.5);
//            leftBack.setPower((rotY - rotX - rx) / denominator * 0.5);
//            rightFront.setPower((rotY - rotX + rx) / denominator * 0.5);
//            rightBack.setPower((rotY + rotX + rx)/ denominator * 0.5);
//        }

        leftFront.setPower((rotY - rotX - rx) / denominator);
        leftBack.setPower((rotY + rotX - rx) / denominator);
        rightFront.setPower((rotY + rotX + rx) / denominator);
        rightBack.setPower((rotY - rotX + rx) / denominator);
//        else {
//            //Field oriented version
//
//            leftFront.setPower((rotY - rotX - rx) / denominator);
//            leftBack.setPower((rotY + rotX - rx) / denominator);
//            rightFront.setPower((rotY + rotX + rx) / denominator);
//            rightBack.setPower((rotY - rotX + rx) / denominator);
//
//            //Robot oriented - retired and is no longer needed
//
////            leftFront.setPower((y + x - rx) / denominator);
////            leftBack.setPower((y - x - rx) / denominator);
////            rightFront.setPower((y - x + rx) / denominator);
////            rightBack.setPower((y + x + rx)/ denominator);
//
//        }



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

}
