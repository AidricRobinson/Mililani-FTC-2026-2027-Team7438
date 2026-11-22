package org.firstinspires.ftc.teamcode;

public class SpeedPIDController {
    private double kP;
    private double kFF;
    private double setPoint;
    private double processVariable;
    private double error;
    private double output;

    public SpeedPIDController (double kP, double kFF) {
        this.kP = kP;
        this.kFF = kFF;
        setPoint = 0;
        processVariable = 0;
        error = 0;
        output = 0;
    }
    public void createSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }
    public void setProcessVariable(double processVariable) {
        this.processVariable = processVariable;
    }
    public double getError() {
        error = setPoint - processVariable;
        return error;
    }
    public double getOutput() {
        output = kP * error + kFF * setPoint;
        output = Math.min(1, output);
        return output;
    }
}
