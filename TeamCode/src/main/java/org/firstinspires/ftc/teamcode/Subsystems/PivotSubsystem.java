package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class PivotSubsystem {
    Servo servoL;
    Servo servoR;

    public PivotSubsystem(OpMode opMode){
        servoL = opMode.hardwareMap.get(Servo.class, "leftServoPivot");
        servoR = opMode.hardwareMap.get(Servo.class, "rightServoPivot");
    }

    public void setPosition(double position){
        servoL.setPosition(position);
        servoR.setPosition(position);
    }
    public void shutdown(){
        servoL.setPosition(0);
        servoR.setPosition(0);
    }
}
