package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;

public class BrakeCommand {
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private Gamepad gamepad;
    private boolean out = false;
    private boolean lastRightBumper = false;

    public BrakeCommand(MecanumDriveSubsystem mecanumDriveSubsystem, Gamepad gamepad) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.gamepad = gamepad;
    }
    public void operate() {
        if (gamepad.left_bumper && !lastRightBumper) {
            out = !out;
        }
        lastRightBumper = gamepad.right_bumper;
        if (out) {
            mecanumDriveSubsystem.setBrakePosition(1);
        } else {
            mecanumDriveSubsystem.setBrakePosition(0);
        }
    }
    public void shutdown() {
        mecanumDriveSubsystem.shutdown();
    }
}
