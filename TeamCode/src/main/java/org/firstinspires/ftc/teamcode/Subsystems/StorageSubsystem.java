package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class StorageSubsystem {
    CRServo spindexer; //motor for spinning the actual spindexer
    DcMotorEx storageMotor; //Motor for spinning the roller/wheel that takes the balls out of the spindexer.

    public StorageSubsystem(OpMode opMode){
        storageMotor = opMode.hardwareMap.get(DcMotorEx.class,"storageMotor");
        storageMotor.setDirection(Constants.PayloadConstants.kStorageMotorDirection);
        storageMotor.setZeroPowerBehavior(Constants.PayloadConstants.kStorageZeroPowerMode);
        spindexer = opMode.hardwareMap.get(CRServo.class, "spindexer");
        spindexer.setDirection(Constants.PayloadConstants.kSpindexerDirection);

    }

    public void setStoragePower(double power){
        storageMotor.setPower(power);
   }

   public void setSpindexerPower(double power){
        spindexer.setPower(power);
   }

    public void shutdown(){
        storageMotor.setPower(0);
        spindexer.setPower(0);
    }
}
