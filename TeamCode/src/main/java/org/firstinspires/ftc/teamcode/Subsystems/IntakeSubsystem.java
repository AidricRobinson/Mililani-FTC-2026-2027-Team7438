package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;

public class IntakeSubsystem {
    DcMotorEx intakeMotor; //only one motor right now, rename to motor one and motor two for second one.

    public IntakeSubsystem(OpMode opMode){
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setDirection(Constants.PayloadConstants.kIntakeMotorDirection);  //change if needs to be other direction(or set motor power to negative)
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(Constants.PayloadConstants.kIntakeMotorZeroPowerMode);  //Brakes when no powered


    }

    public void setIntakePower(double power){
        intakeMotor.setPower(power);

    }


    public void shutdown(){
        intakeMotor.setPower(0);
    }


}
