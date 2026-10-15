package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Constants {
    public static class PinpointConstants {

        public static final double kPodXOffset = -6.46875; // in inches
        public static final double kPodYOffset = -8.0937; // in inches
        public static final GoBildaPinpointDriver.EncoderDirection kPodXDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED;
        public static final GoBildaPinpointDriver.EncoderDirection kPodYDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;

        public static final double kOmniDiameter = 60; // in mm
        public static final double kOmniCircumference = kOmniDiameter * Math.PI;
        public static final double kEncoderCountsPerRevolution = 8192;
        public static final double kEncoderResolution = kEncoderCountsPerRevolution / kOmniCircumference;


        public static final double kRobotWidth = 8; // placeholder, in inches
        public static final double kRobotLength = 9; // placeholder in inches
        public static final double kLimelightToFieldOffsetX = 72;
        public static final double kLimelightToFieldOffsetY = 72;

//        public static final double[] kBLcorner = {0, 0};
//        public static final double[] kBRcorner = {144, 0};
//        public static final double[] kTLcorner = {144, 0};
//        public static final double[] kTRcorner = {144, 144};

        public static final double[] kTopRedHive = {1, 1}; // in inches
        public static final double[] kBottomRedHive = {1, 0};  // in inches
        public static final double[] kTopBlueHive = {0, 1};  // in inches
        public static final double[] kBottomBlueHive = {0, 0};  // in inches

        public static final String blueAlliance = "BLUE";
        public static final String redAlliance = "RED";



        public static final double kLimeNA = 1;
        public static final double kLimeNB = 1;
        public static final double kLimeNC = 1;
        public static final double kLimePA = 1;
        public static final double kLimePB = 1;
        public static final double kLimePC = 1;

        public static final double kPoseNA = 1;
        public static final double kPoseNB = 1;
        public static final double kPoseNC = 1;
        public static final double kPosePA = 1;
        public static final double kPosePB = 1;
        public static final double kPosePC = 1;
    }
    public static class EncoderConstants{

    }
    public static class AprilTagConstants {

    }

    public static class PayloadConstants {
        public static final PIDController nectarPID = new PIDController(0, 0, 0, 0);
        public static final PIDController pollenPID = new PIDController(0, 0, 0, 0);
        public static final DcMotorEx.Direction kStorageMotorDirection = DcMotorEx.Direction.FORWARD;
        public static final CRServo.Direction kSpindexerDirection = CRServo.Direction.FORWARD;
        public static final DcMotorEx.Direction kNectarShooterDirection = DcMotorEx.Direction.FORWARD;
        public static final DcMotorEx.Direction kPollenShooterDirection = DcMotorEx.Direction.FORWARD;
        public static final DcMotorEx.Direction kIntakeMotorDirection = DcMotorEx.Direction.FORWARD;

        public static final DcMotorEx.ZeroPowerBehavior kNectarShooterZeroPowerMode = DcMotorEx.ZeroPowerBehavior.FLOAT;
        public static final double kShooterTicksPerRev = 28;
        public static final DcMotorEx.ZeroPowerBehavior kPollenShooterZeroPowerMode = DcMotorEx.ZeroPowerBehavior.FLOAT;
        public static final DcMotorEx.ZeroPowerBehavior kStorageZeroPowerMode = DcMotorEx.ZeroPowerBehavior.BRAKE;
        public static final DcMotorEx.ZeroPowerBehavior kIntakeMotorZeroPowerMode = DcMotorEx.ZeroPowerBehavior.FLOAT;
    }
}
