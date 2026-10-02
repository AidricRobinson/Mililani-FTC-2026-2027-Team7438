package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;


@TeleOp(name="TEST ONLY")

public class TestTeleOp extends OpMode {
    private LLResult result;



    //declaring subsystems and commands here
    private MecanumDriveSubsystem mecanumDriveSubsystem;
//    private IntakeCommand intakeCommand;
//    private StorageCommand storageCommand;

//    private IntakeSubsystem intakeSubsystem;
//    private StorageSubsystem storageSubsystem;


    public void init () {
        mecanumDriveSubsystem = new MecanumDriveSubsystem(this.hardwareMap,this);
//        intakeSubsystem = new IntakeSubsystem(this);
//        storageSubsystem = new StorageSubsystem(this) ;

//        intakeCommand = new IntakeCommand(intakeSubsystem, gamepad1);

//        storageCommand = new StorageCommand(storageSubsystem, gamepad1);

    }

    @Override
    public void loop(){

//        intakeCommand.operate(gamepad1);
        mecanumDriveSubsystem.operate(gamepad1, telemetry, Constants.PinpointConstants.redAlliance);
//        storageCommand.operate(gamepad1);



    }
    public void stop(){
//        intakeCommand.shutdown();
        mecanumDriveSubsystem.shutdown();
//        storageCommand.shutdown();

    }
}