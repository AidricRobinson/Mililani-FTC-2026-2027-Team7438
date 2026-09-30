package org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Subsystems.*;

public class MotorCommand {
    MotorSubsystem motorSubsystem;
    Gamepad gamepad;

    public MotorCommand(MotorSubsystem motorSubsystem, Gamepad gamepad){
        this.motorSubsystem = motorSubsystem;
        this.gamepad = gamepad;
    }
    public void operate(Gamepad gamepad){
        if (gamepad.a){
            motorSubsystem.setMotorPower(1);
        }
        else {
            motorSubsystem.setMotorPower(0);
        }
    }
    public void shutdown(){
        motorSubsystem.shutdown();
    }
}