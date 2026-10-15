package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoIntake {
    private IntakeSubsystem intakeSubsystem;
    private StorageSubsystem storageSubsystem;
    private Gamepad gamepad;
    public AutoIntake(IntakeSubsystem intakeSubsystem, StorageSubsystem storageSubsystem, Gamepad gamepad) {
        this.intakeSubsystem = intakeSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.gamepad = gamepad;
    }

    public void operate() {
        if (gamepad.left_bumper) {
            intakeSubsystem.requestPower(0.5);
            storageSubsystem.requestSpindexerPower(1);
            storageSubsystem.requestStoragePower(0.5);
        }
    }
    public void shutdown() {
        intakeSubsystem.shutdown();
        storageSubsystem.shutdown();
    }

}
