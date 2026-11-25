package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;

public class AutoFlowerIntake {
    private IntakeSubsystem intakeSubsystem;
    private TransferSubsystem transferSubsystem;
    private PivotSubsystem pivotSubsystem;
    private Gamepad gamepad;

    public AutoFlowerIntake(IntakeSubsystem intakeSubsystem, TransferSubsystem transferSubsystem, PivotSubsystem pivotSubsystem, Gamepad gamepad) {
        this.pivotSubsystem = pivotSubsystem;
        this.transferSubsystem = transferSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.gamepad = gamepad;
    }

    public void operate() {
        if (gamepad.left_trigger >= 0.2) {
            intakeSubsystem.requestPower(0.5);
            transferSubsystem.requestBottomHarvester(1);
            pivotSubsystem.requestPosition(1);

//            if (!transferSubsystem.beamBroken()) {
                transferSubsystem.requestTopHarvester(.5);
//            }
        }
    }
    public void shutdown() {
        intakeSubsystem.shutdown();
        transferSubsystem.shutdown();
    }
}
