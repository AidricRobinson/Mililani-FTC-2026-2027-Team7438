package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class MotorSubsystem {
    DcMotorEx Motor1;
    DcMotorEx Motor2;

        public MotorSubsystem(OpMode opMode){
            Motor1 = opMode.hardwareMap.get(DcMotorEx.class, "Motor0");
            Motor2 = opMode.hardwareMap.get(DcMotorEx.class,"Motor1");

            Motor1.setDirection(DcMotorEx.Direction.FORWARD);
            Motor2.setDirection(DcMotorEx.Direction.FORWARD);

            Motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            Motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
        public void setMotorPower(double power){
            Motor1.setPower(power);
            Motor2.setPower(power);
        }
        public double getMotorEncoder(){
            return Motor1.getCurrentPosition();
        }
        public void setMotorVelocity(double rpm){
            Motor1.setVelocity(rpm);
        }
        public double getMotorVelocity(){
            return Motor1.getVelocity();
        }
        public void shutdown(){
            Motor1.setPower(0);
        }
//        public boolean isBusyCheck(){
//            boolean isBusy = true;
//            if (Motor1.isBusy() == true )
//        }
}