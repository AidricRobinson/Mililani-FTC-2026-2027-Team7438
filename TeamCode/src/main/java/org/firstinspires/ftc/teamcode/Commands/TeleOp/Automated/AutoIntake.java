package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TransferSubsystem;

public class AutoIntake {
    private IntakeSubsystem intakeSubsystem;
    private TransferSubsystem transferSubsystem;
    private Gamepad gamepad;
    public AutoIntake(IntakeSubsystem intakeSubsystem, TransferSubsystem transferSubsystem, Gamepad gamepad) {
        this.intakeSubsystem = intakeSubsystem;
        this.transferSubsystem = transferSubsystem;
        this.gamepad = gamepad;
    }

    public void operate() {
        if (gamepad.left_trigger_pressed) {
            intakeSubsystem.requestPower(0.9);

            transferSubsystem.requestBottomHarvester(1);
//            if (!transferSubsystem.beamBroken()) {
                transferSubsystem.requestTopHarvester(.5);
//            }
        }
    }
    public void shutdown() {
        intakeSubsystem.shutdown();
//        storageSubsystem.shutdown();
        transferSubsystem.shutdown();
    }

}
