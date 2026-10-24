package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants;

public class StorageSubsystem {
    private CRServo spindexer; //motor for spinning the actual spindexer
    private DcMotorEx storageMotor; //Motor for spinning the roller/wheel that takes the balls out of the spindexer.

    private double testPower;
    public StorageSubsystem(OpMode opMode){
        storageMotor = opMode.hardwareMap.get(DcMotorEx.class,"storageMotor");
        storageMotor.setDirection(Constants.PayloadConstants.kStorageMotorDirection);
        storageMotor.setZeroPowerBehavior(Constants.PayloadConstants.kStorageZeroPowerMode);
        storageMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        spindexer = opMode.hardwareMap.get(CRServo.class, "spindexer");
        spindexer.setDirection(Constants.PayloadConstants.kSpindexerDirection);


    }

    public void setTestPower(double power) {
        testPower = power;
    }
    public void increasePower(double power) {
        testPower += power;
    }
    public void decreasePower(double power) {
        testPower -= power;
    }
    public double getTestPower() {
        return testPower;
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


    private double storageRequest = 0;
    private double spindexerRequest = 0;

    public void requestStoragePower(double power) {
        storageRequest = power;
    }

    public void requestSpindexerPower(double power) {
        spindexerRequest = power;
    }

    /** Call once at the END of loop(). */
    public void update() {
        storageMotor.setPower(storageRequest);
        spindexer.setPower(spindexerRequest);
        storageRequest = 0;
        spindexerRequest = 0;
    }
}
