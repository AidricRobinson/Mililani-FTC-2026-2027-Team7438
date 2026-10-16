package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants;

public class ShooterSubsystem {
    private double pollenRPM;
    private double nectarRPM;
    DcMotorEx nectarShooter;
    DcMotorEx pollenShooter;
    private static boolean pollenToggle;
    private static boolean nectarToggle;
    private static boolean manualToggle;

    public ShooterSubsystem(OpMode opMode){
        nectarShooter = opMode.hardwareMap.get(DcMotorEx.class, "nectarShooter");
        pollenShooter = opMode.hardwareMap.get(DcMotorEx.class, "pollenShooter");

        nectarShooter.setDirection(Constants.PayloadConstants.kNectarShooterDirection);
        pollenShooter.setDirection(Constants.PayloadConstants.kPollenShooterDirection);

        nectarShooter.setZeroPowerBehavior(Constants.PayloadConstants.kNectarShooterZeroPowerMode);
        pollenShooter.setZeroPowerBehavior(Constants.PayloadConstants.kPollenShooterZeroPowerMode);

        pollenToggle = true;
        nectarToggle = false;
        manualToggle = false;
        pollenRPM = 2000;
        nectarRPM = 2000;
    }

    public void increasePollenRPM(double rpm) {
        pollenRPM += rpm;
    }
    public void decreasePollenRPM(double rpm) {
        pollenRPM -= rpm;
    }
    public void increaseNectarRPM(double rpm) {
        nectarRPM += rpm;
    }
    public void decreaseNectarRPM(double rpm) {
        nectarRPM -= rpm;
    }
    public double getTestPollenRPM() {
        return pollenRPM;
    }
    public double getTestNectarRPM() {
        return nectarRPM;
    }
    public void setTestPollenRPM(double rpm) {
        pollenRPM = rpm;
    }
    public void setTestNectarRPM(double rpm) {
        nectarRPM = rpm;
    }

    public void setNectarToggle(boolean toggle) {
        nectarToggle = toggle;
    }
    public void setPollenToggle(boolean toggle) {
        pollenToggle = toggle;
    }
    public boolean getNectarToggle() {
        return nectarToggle;
    }
    public boolean getPollenToggle() {
        return pollenToggle;
    }
    public void setManualToggle(boolean toggle) {
        manualToggle = toggle;
    }
    public boolean getManualToggle() {
        return manualToggle;
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


    public void setNectarShooterRPM(double rpm){
        nectarShooter.setVelocity(rpm * Constants.PayloadConstants.kShooterTicksPerRev / 60.0);
    }
    public void setPollenShooterRPM(double rpm){
        pollenShooter.setVelocity(rpm * Constants.PayloadConstants.kShooterTicksPerRev / 60.0);
    }
    public double getNectarShooterRPM() {
        return nectarShooter.getVelocity() * 60.0 / Constants.PayloadConstants.kShooterTicksPerRev;
    }
    public double getPollenShooterRPM() {
        return pollenShooter.getVelocity() * 60.0 / Constants.PayloadConstants.kShooterTicksPerRev;
    }
    public void shutdown(){
        pollenShooter.setPower(0);
        nectarShooter.setPower(0);
    }


    private double nectarRequest = 0;
    private double pollenRequest = 0;

    public void requestNectarPower(double power) {
        nectarRequest = power;
    }

    public void requestPollenPower(double power) {
        pollenRequest = power;
    }

    /** Call once at the END of loop(). Flywheels stop if nobody asked. */
    public void update() {
        nectarShooter.setPower(nectarRequest);
        pollenShooter.setPower(pollenRequest);
        nectarRequest = 0;
        pollenRequest = 0;
    }
}
