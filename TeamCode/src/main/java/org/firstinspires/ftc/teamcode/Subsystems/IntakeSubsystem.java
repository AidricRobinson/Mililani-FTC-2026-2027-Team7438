package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants;

public class IntakeSubsystem {
    private DcMotorEx intakeMotor; //only one motor right now, rename to motor one and motor two for second one.
    private double testPower;

    public IntakeSubsystem(OpMode opMode){
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setDirection(Constants.PayloadConstants.kIntakeMotorDirection);  //change if needs to be other direction(or set motor power to negative)
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(Constants.PayloadConstants.kIntakeMotorZeroPowerMode);  //Brakes when no powered

        testPower = 0;
    }

    public void setIntakePower(double power){
        intakeMotor.setPower(power);

    }

    public void increaseTestPower(double power) {
        testPower += power;
    }
    public void decreaseTestPower(double power) {
        testPower -= power;
    }
    public void setTestPower() {
        intakeMotor.setPower(testPower);
    }
    public void zeroTestPower() {
        testPower = 0;
    }
    public double getTestPower() {
        return testPower;
    }
// test

    public void shutdown(){
        intakeMotor.setPower(0);
    }


    private double requestedPower = 0;

    public void requestPower(double power) {
        requestedPower = power;
    }

    /** Call once at the END of loop(). Applies the last request (0 if none). */
    public void update() {
        intakeMotor.setPower(requestedPower);
        requestedPower = 0;
    }


}
