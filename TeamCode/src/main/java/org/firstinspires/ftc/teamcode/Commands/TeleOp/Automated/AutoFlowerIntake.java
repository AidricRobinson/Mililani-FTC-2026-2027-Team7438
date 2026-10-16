package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

public class AutoFlowerIntake {
    private IntakeSubsystem intakeSubsystem;
    private StorageSubsystem storageSubsystem;
    private PivotSubsystem pivotSubsystem;
    private Gamepad gamepad;

    public AutoFlowerIntake(IntakeSubsystem intakeSubsystem, StorageSubsystem storageSubsystem, PivotSubsystem pivotSubsystem, Gamepad gamepad) {
        this.pivotSubsystem = pivotSubsystem;
        this.storageSubsystem = storageSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.gamepad = gamepad;
    }

    public void operate() {
        if (gamepad.left_trigger >= 0.2) {
            intakeSubsystem.requestPower(0.5);
            storageSubsystem.requestSpindexerPower(1);
            storageSubsystem.requestStoragePower(0.5);
            pivotSubsystem.requestPosition(1);
        }
    }
    public void shutdown() {
        intakeSubsystem.shutdown();
        storageSubsystem.shutdown();
    }
}
