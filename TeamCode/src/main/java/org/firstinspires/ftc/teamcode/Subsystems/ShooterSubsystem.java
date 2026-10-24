package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants;

public class ShooterSubsystem {
    private DcMotorEx leftFlywheel;
    private DcMotorEx rightFlywheel;
    private double testRPM;
    private double testPower;
    private double currentPosition;
    private double lastPosition;
    private double requestedPower;
    private static boolean manualToggle = false;


    public ShooterSubsystem (OpMode opMode) {
        leftFlywheel = opMode.hardwareMap.get(DcMotorEx.class, "leftFlywheel");
        rightFlywheel = opMode.hardwareMap.get(DcMotorEx.class, "rightFlywheel");

        leftFlywheel.setDirection(Constants.PayloadConstants.kLeftFlywheelDirection);
        rightFlywheel.setDirection(Constants.PayloadConstants.kRightFlywheelDirection);

        leftFlywheel.setZeroPowerBehavior(Constants.PayloadConstants.kLeftFlywheelZeroPowerMode);
        rightFlywheel.setZeroPowerBehavior(Constants.PayloadConstants.kRightFlywheelZeroPowerMode);

        leftFlywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFlywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void increaseRPM(double rpm) {
        testRPM += rpm;
    }
    public void decreaseRPM(double rpm) {
        testRPM -= rpm;
    }
    public void setTestRPM(double rpm) {
        testRPM = rpm;
    }
    public double getTestRPM() {
        return testRPM;
    }
    public void increasePower(double power) {
        testPower += power;
    }
    public void decreasePower(double power) {
        testPower -= power;
    }
    public void setTestPower(double power) {
        testPower = power;
    }
    public double getTestPower() {
        return testPower;
    }
    public void setPower(double power) {
        leftFlywheel.setPower(power);
        rightFlywheel.setPower(power);
    }
    public double getLeftVelocity() {
        return leftFlywheel.getVelocity();
    }
    public double getRightVelocity() {
        return rightFlywheel.getVelocity();
    }
    public void setVelocity(double velocity) {
        leftFlywheel.setVelocity(velocity);
        rightFlywheel.setVelocity(velocity);
    }
    public double getLeftCP() {
        return leftFlywheel.getCurrentPosition();
    }
    public double getRightCP() {
        return rightFlywheel.getCurrentPosition();
    }
    public void shutdown() {
        leftFlywheel.setPower(0);
        rightFlywheel.setPower(0);
    }

    public void requestPower(double power) {
        requestedPower = power;
    }

    public void setManualToggle(boolean toggle) {
        manualToggle = toggle;
    }
    public boolean getManualToggle() {
        return manualToggle;
    }

    /** Call once at the END of loop(). Applies the last request (0 if none). */
    public void update() {
        leftFlywheel.setPower(requestedPower);
        rightFlywheel.setPower(requestedPower);
        requestedPower = 0;

        lastPosition = currentPosition;
        currentPosition = getLeftCP();

    }
    public double getRPM(double dt) {
        return (currentPosition - lastPosition) / dt / 28 * 60;
    }

}
