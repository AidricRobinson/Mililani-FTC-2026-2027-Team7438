package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

public class PinpointLocalizer {

    private GoBildaPinpointDriver pinpoint;

    public PinpointLocalizer(GoBildaPinpointDriver pinpoint) {
        this.pinpoint = pinpoint;
    }
    public void setPose(Pose2D pose) {
        pinpoint.setPosition(pose);
    }
    public void setPosX(double x) {
        pinpoint.setPosX(x, DistanceUnit.INCH);
    }
    public void setPosY(double y) {
        pinpoint.setPosY(y, DistanceUnit.INCH);
    }
    public void setHeading(double degrees) {
        pinpoint.setHeading(degrees, AngleUnit.DEGREES);
    }

    public void update() {
        pinpoint.update();
    }

    public Pose2D getPose() {
        return pinpoint.getPosition();
    }

    public double getY() {
        return pinpoint.getPosition().getX(DistanceUnit.INCH);
    }

    public double getX() {
        return pinpoint.getPosition().getY(DistanceUnit.INCH);
    }

    public double getHeading() {
        return pinpoint.getHeading(AngleUnit.DEGREES);
    }
    public double getHeadingRadians() {
        return pinpoint.getHeading(AngleUnit.RADIANS);
    }

    public double getVelocityY() {
        return pinpoint.getVelX(DistanceUnit.INCH);
    }

    public double getVelocityX() {
        return pinpoint.getVelY(DistanceUnit.INCH);
    }
    public double getFieldYVelocity() {
        return getVelocityY() * Math.cos(Math.toRadians(getHeading())) - getVelocityX()*Math.sin(Math.toRadians(getHeading()));
    }
    public double getFieldXVelocity() {
        return getVelocityY() * Math.sin(Math.toRadians(getHeading())) + getVelocityX() * Math.cos(Math.toRadians(getHeading()));
    }

    public double getHeadingVelocity() {
        return pinpoint.getHeadingVelocity(
                UnnormalizedAngleUnit.DEGREES);
    }
}