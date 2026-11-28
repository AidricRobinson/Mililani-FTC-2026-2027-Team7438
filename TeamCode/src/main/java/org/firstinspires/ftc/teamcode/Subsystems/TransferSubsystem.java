package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.Constants;

public class TransferSubsystem {
    private DcMotor topHarvester; //Motor for spinning the roller/wheel that takes the balls out of the spindexer.
    private CRServo leftServo;
//    private CRServo rightServo;
//    private DigitalChannel irSensor;
    private double testPower;
    public TransferSubsystem(OpMode opMode){
        topHarvester = opMode.hardwareMap.get(DcMotor.class,"top");
        topHarvester.setDirection(Constants.PayloadConstants.kStorageMotorDirection);
        topHarvester.setZeroPowerBehavior(Constants.PayloadConstants.kStorageZeroPowerMode);
        topHarvester.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftServo = opMode.hardwareMap.get(CRServo.class, "lHarv");
        leftServo.setDirection(DcMotorSimple.Direction.FORWARD);

//        rightServo = opMode.hardwareMap.get(CRServo.class, "rHarv");
//        rightServo.setDirection(DcMotorSimple.Direction.REVERSE);

//        irSensor = opMode.hardwareMap.get(DigitalChannel.class, "ir");
//        irSensor.setMode(DigitalChannel.Mode.OUTPUT);
    }
//    public boolean beamBroken() {
//        return irSensor.getState();
//    }
//    public double getVelocity() {
//        return topHarvester.setTargetPosition();
//    }
//    public double getRPM() {
//        return getVelocity() / 28 * 60;
//    }

    public void setTestPower(double power) {
        testPower = power;
    }


    public double getTestPower() {
        return testPower;
    }

    public void setTopHarvesterPower(double power){
        topHarvester.setPower(power);
    }
    public void setBottomHarvesterPower(double power) {
        leftServo.setPower(power);
//        rightServo.setPower(0);
    }
    public void shutdown(){
        topHarvester.setPower(0);
        leftServo.setPower(0);
//        rightServo.setPower(0);
    }


    private double topHarvesterRequest = 0;
    private double bottomHarvesterRequest = 0;

    public void requestTopHarvester(double power) {
        topHarvesterRequest = power;
    }
    public void requestBottomHarvester(double power) {
        bottomHarvesterRequest = power;
    }


    /** Call once at the END of loop(). */
    public void update() {
        topHarvester.setPower(topHarvesterRequest);
        leftServo.setPower(bottomHarvesterRequest);
//        rightServo.setPower(bottomHarvesterRequest);
        topHarvesterRequest = 0;
        bottomHarvesterRequest = 0;
    }
}
