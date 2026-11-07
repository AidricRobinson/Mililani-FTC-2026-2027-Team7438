package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class PivotSubsystem {
    Servo servoL;
    Servo servoR;

    public PivotSubsystem(OpMode opMode){
        servoL = opMode.hardwareMap.get(Servo.class, "lPivot");
        servoR = opMode.hardwareMap.get(Servo.class, "rPivot");

    }

    public void setPosition(double position){
        servoL.setPosition(position);
        servoR.setPosition(position);
    }
    public void setLeftPosition(double position) {
        servoL.setPosition(position);
    }
    public void setRightPosition(double position) {
        servoR.setPosition(position);
    }
    public void setPivotUp() {
        setLeftPosition(0);
        setRightPosition(1);
    }
    public void setPivotDown() {
        setLeftPosition(1);
        setRightPosition(0);
    }
    public void shutdown(){
        servoL.setPosition(0);
        servoR.setPosition(0);
    }

    private double requestedPosition = 0;
    private boolean hasRequest = false;

    public void requestPosition(double position) {
        requestedPosition = position;
        hasRequest = true;
    }

    /** Call once at the END of loop(). Does nothing if nobody asked. */
    public void update() {
        if (hasRequest) {
            setPosition(requestedPosition);
        }
        hasRequest = false;
    }
}
