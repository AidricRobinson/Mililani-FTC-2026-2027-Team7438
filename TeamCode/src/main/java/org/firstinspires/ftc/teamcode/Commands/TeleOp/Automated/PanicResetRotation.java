package org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Localization;

public class PanicResetRotation {
    private Gamepad gamepad;

    public PanicResetRotation(Gamepad gamepad) {
        this.gamepad = gamepad;
    }
    public void operate(Localization localization) {
        if (gamepad.dpad_up) {
            localization.resetRotation(90);
        }
        else if (gamepad.dpad_right) {
            localization.resetRotation(0);
        }
        else if (gamepad.dpad_down) {
            localization.resetRotation(-90);
        }
        else if (gamepad.dpad_left) {
            localization.resetRotation(179.999);
        }
    }
}
