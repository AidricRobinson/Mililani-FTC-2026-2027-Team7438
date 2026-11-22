package org.firstinspires.ftc.teamcode;

public class PIDController {
    private final double kP;
    private final double kFF;
    private double setPoint;
    private double processVariable;
    private double error;
    public double outputMin = -1;      // flywheels should never be driven backward
    public double outputMax = 1;

    // kFF is now feedforward PER UNIT OF SETPOINT (about 1 / max RPM, e.g. 1/6000 = 0.000167)
    public PIDController(double kP, double kFF) {
        this.kP = kP;
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

    public double getOutput() {
        error = getError();





        double output = kP * error + Math.copySign(kFF, kP);
        return clamp(output, outputMin, outputMax);
    }



    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}

