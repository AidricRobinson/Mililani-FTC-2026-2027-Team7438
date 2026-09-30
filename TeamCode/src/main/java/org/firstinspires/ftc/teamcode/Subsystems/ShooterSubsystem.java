package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants;

public class ShooterSubsystem {
    DcMotorEx nectarShooter;
    DcMotorEx pollenShooter;

    public ShooterSubsystem(OpMode opMode){
        nectarShooter = opMode.hardwareMap.get(DcMotorEx.class, "nectarShooter");
        pollenShooter = opMode.hardwareMap.get(DcMotorEx.class, "pollenShooter");

        nectarShooter.setDirection(Constants.PayloadConstants.kNectarShooterDirection);
        pollenShooter.setDirection(Constants.PayloadConstants.kPollenShooterDirection);

        nectarShooter.setZeroPowerBehavior(Constants.PayloadConstants.kNectarShooterZeroPowerMode);
        pollenShooter.setZeroPowerBehavior(Constants.PayloadConstants.kPollenShooterZeroPowerMode);
    }

    public void setNectarShooterPower(double power){
        nectarShooter.setPower(power);
    }

    public void setPollenShooterPower(double power){
        pollenShooter.setPower(power);
    }
    public void setBothShooterPower(double power){
        pollenShooter.setPower(power);
        nectarShooter.setPower(power);
    }

    public void NectarShutdown(){
        nectarShooter.setPower(0);
    }

    public void PollenShutdown(){
        pollenShooter.setPower(0);
    }

    public double getNectarShooterRpm(){
        return nectarShooter.getVelocity();
    }
    public double getPollenShooterRpm(){
       return pollenShooter.getVelocity();
    }

    public void setNectarShooterRpm(double rpm){
        nectarShooter.setVelocity(rpm);
    }
    public void setPollenShooterRpm(double rpm){
        pollenShooter.setVelocity(rpm);
    }

    public void shutdown(){
        pollenShooter.setPower(0);
        nectarShooter.setPower(0);
    }
}
