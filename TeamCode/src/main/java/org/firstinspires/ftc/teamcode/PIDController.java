package org.firstinspires.ftc.teamcode;

public class PIDController {
    private final double kP, kI, kD, kFF;
    private double setPoint;
    private double processVariable;
    private double error;
    private double integral;
    private double prevError;
    private boolean firstRun = true;

    public double integralLimit = 0.3;  // max power the I-term may contribute
    public double outputMin = -1;      // flywheels should never be driven backward
    public double outputMax = 1;

    // kFF is now feedforward PER UNIT OF SETPOINT (about 1 / max RPM, e.g. 1/6000 = 0.000167)
    public PIDController(double kP, double kI, double kD, double kFF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kFF = kFF;
    }

    public void createSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public void setProcessVariable(double processVariable) {
        this.processVariable = processVariable;
    }

    public double getError() {            // no side effects
        return setPoint - processVariable;
    }

    public double getOutput(double dt) {
        error = getError();

        if (dt > 0) {
            integral += error * dt;
            if (kI != 0) {
                double max = integralLimit / Math.abs(kI);
                integral = clamp(integral, -max, max);
            }
        }

        double derivative = 0;
        if (!firstRun && dt > 0) {
            derivative = (error - prevError) / dt;
        }
        prevError = error;
        firstRun = false;

        double output = kFF * setPoint + kP * error + kI * integral + kD * derivative;
        return clamp(output, outputMin, outputMax);
    }

    public void resetIntegral() {
        integral = 0;
        firstRun = true;
    }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}

