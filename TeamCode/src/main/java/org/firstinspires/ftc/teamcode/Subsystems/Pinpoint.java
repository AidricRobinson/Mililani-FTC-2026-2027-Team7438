//package org.firstinspires.ftc.teamcode.Subsystems;
//
//import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
//import org.firstinspires.ftc.teamcode.Constants;
//
//public class Pinpoint {
//    Pose2D pose2d;
//    GoBildaPinpointDriver pinpoint;
//    // does not need object for the individual odometry pods
//
//    public Pinpoint(HardwareMap hardwareMap, OpMode opMode) {
//        // EXAMPLE STARTING CONFIG
//
//
//        pinpoint = opMode.hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
//        pinpoint.setEncoderResolution(Constants.PinpointConstants.kEncoderResolution, DistanceUnit.MM);
//        pinpoint.setOffsets(Constants.PinpointConstants.kPodXOffset,
//                Constants.PinpointConstants.kPodYOffset,
//                DistanceUnit.CM);
//
//        pinpoint.setEncoderDirections(Constants.PinpointConstants.kPodXDirection,
//                Constants.PinpointConstants.kPodYDirection);
//
//        pinpoint.initialize();
//
//        pinpoint.setPosX(12, DistanceUnit.INCH);
//        pinpoint.setPosY(12, DistanceUnit.INCH);
//        pinpoint.setHeading(90, AngleUnit.DEGREES);
//    }
//
//    public void operate (Telemetry telemetry) {
//
//        pinpoint.update();
//        getRobotPose();
//        telemetry.addData("Pose X: ", getPoseX());
//        telemetry.addData("Pose Y: ", getPoseY());
//        telemetry.addData("Rotation: ", getPoseRotation());
//        telemetry.update();
//    }
//
//    public Pose2D getRobotPose() {
//        pose2d = pinpoint.getPosition();
//        return pose2d;
//    }
//    public double getPoseX() {
//        return pinpoint.getPosX(DistanceUnit.INCH);
//    }
//    public double getPoseY() {
//        return pinpoint.getPosY(DistanceUnit.INCH);
//    }
//    public double getPoseRotation() {
//        return pinpoint.getHeading(AngleUnit.DEGREES);
//    }
//
//    public void setPose2d(double x, double y) {
//        pinpoint.setPosX(x, DistanceUnit.INCH);
//        pinpoint.setPosY(y, DistanceUnit.INCH);
//    }
//
//    public double getDistance(double targetX, double targetY) {
//        return Math.sqrt(
//                Math.pow(Math.abs(targetX - getPoseX()), 2)
//                        + Math.pow(Math.abs(targetY - getPoseY()), 2)
//        );
//    }
//
//    public double getTargetRotation(double targetX, double targetY) {
//        double changeX = targetX - getPoseX();
//        double changeY = targetY - getPoseY();
//
//        if (changeX > 0) {
//            return Math.toDegrees(Math.atan2(changeY, changeX));
//        }
//        if (changeY > 0) {
//            return Math.toDegrees(Math.atan2(changeY, changeX)) + 180;
//        }
//        return Math.toDegrees(Math.atan2(changeY, changeX)) - 180;
//
//
//    }
//
//}
