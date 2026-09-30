//package org.firstinspires.ftc.teamcode.Commands.Autonomous;
//
//
//import com.qualcomm.robotcore.hardware.Gamepad;
//import com.sun.tools.javac.code.Attribute;
//
//import org.firstinspires.ftc.teamcode.Constants;
//import org.firstinspires.ftc.teamcode.Subsystems.Pinpoint;
//
//public class RelocalizationCommand {
//    Pinpoint pinpoint;
//    Gamepad gamepad;
//
//    public RelocalizationCommand (Pinpoint pinpoint, Gamepad gamepad) {
//        this.pinpoint = pinpoint;
//        this.gamepad = gamepad;
//    }
//
//    public void operate(Gamepad gamepad) {
//        if (gamepad.left_bumper) {
//            double blDistance = Math.sqrt(Math.pow(Math.abs(Constants.PinpointConstants.kBLcorner[0] - pinpoint.getPoseX()), 2)
//                    + Math.pow(Math.abs(Constants.PinpointConstants.kBLcorner[1] - pinpoint.getPoseY()), 2));
//            double brDistance = Math.sqrt(Math.pow(Math.abs(Constants.PinpointConstants.kBRcorner[0] - pinpoint.getPoseX()), 2)
//                    + Math.pow(Math.abs(Constants.PinpointConstants.kBRcorner[1] - pinpoint.getPoseY()), 2));
//            double tlDistance = Math.sqrt(Math.pow(Math.abs(Constants.PinpointConstants.kTLcorner[0] - pinpoint.getPoseX()), 2)
//                    + Math.pow(Math.abs(Constants.PinpointConstants.kTLcorner[1] - pinpoint.getPoseY()), 2));
//            double trDistance = Math.sqrt(Math.pow(Math.abs(Constants.PinpointConstants.kTRcorner[0] - pinpoint.getPoseX()), 2)
//                    + Math.pow(Math.abs(Constants.PinpointConstants.kTRcorner[1] - pinpoint.getPoseY()), 2));
//
//            double min = Math.min(Math.min(Math.min(blDistance, brDistance), tlDistance), trDistance);
//            if (min == blDistance) {
//                if (Math.abs(Math.abs(pinpoint.getPoseRotation()) - 180) <= 5
//                        || Math.abs(pinpoint.getPoseRotation()) <= 5) {
//                    pinpoint.setPose2d(Constants.PinpointConstants.kRobotLength,
//                            Constants.PinpointConstants.kRobotWidth);
//                }
//                if (Math.abs(Math.abs(pinpoint.getPoseRotation()) - 90) <= 5) {
//                    pinpoint.setPose2d(Constants.PinpointConstants.kRobotWidth,
//                            Constants.PinpointConstants.kRobotLength);
//                }
//            } else if (min == brDistance) {
//                if (Math.abs(Math.abs(pinpoint.getPoseRotation()) - 180) <= 5
//                        || Math.abs(pinpoint.getPoseRotation()) <= 5) {
//                    pinpoint.setPose2d(144 - Constants.PinpointConstants.kRobotLength,
//                            Constants.PinpointConstants.kRobotWidth);
//                }
//                if (Math.abs(Math.abs(pinpoint.getPoseRotation()) - 90) <= 5) {
//                    pinpoint.setPose2d(144 - Constants.PinpointConstants.kRobotWidth,
//                            Constants.PinpointConstants.kRobotLength);
//                }
//            } else if (min == tlDistance) {
//                if (Math.abs(Math.abs(pinpoint.getPoseRotation()) - 180) <= 5
//                        || Math.abs(pinpoint.getPoseRotation()) <= 5) {
//                    pinpoint.setPose2d(Constants.PinpointConstants.kRobotLength,
//                            144 - Constants.PinpointConstants.kRobotWidth);
//                }
//                if (Math.abs(Math.abs(pinpoint.getPoseRotation()) - 90) <= 5) {
//                    pinpoint.setPose2d(Constants.PinpointConstants.kRobotWidth,
//                            144 - Constants.PinpointConstants.kRobotLength);
//                }
//            } else if (min == trDistance) {
//                if (Math.abs(Math.abs(pinpoint.getPoseRotation()) - 180) <= 5
//                        || Math.abs(pinpoint.getPoseRotation()) <= 5) {
//                    pinpoint.setPose2d(144 - Constants.PinpointConstants.kRobotLength,
//                            144 - Constants.PinpointConstants.kRobotWidth);
//                }
//                if (Math.abs(Math.abs(pinpoint.getPoseRotation()) - 90) <= 5) {
//                    pinpoint.setPose2d(144 - Constants.PinpointConstants.kRobotWidth,
//                            144 - Constants.PinpointConstants.kRobotLength);
//                }
//            }
//        }
//    }
//}
