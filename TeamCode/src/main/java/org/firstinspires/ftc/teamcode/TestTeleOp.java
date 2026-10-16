package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Commands.Test.TestAutoAlign;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;


@TeleOp(name="TEST ONLY")
@Config
public class TestTeleOp extends OpMode {

    GoBildaPinpointDriver pinpoint;



    //declaring subsystems and commands here
    private MecanumDriveSubsystem mecanumDriveSubsystem;

//    private IntakeCommand intakeCommand;
//    private StorageCommand storageCommand;

//    private IntakeSubsystem intakeSubsystem;
//    private StorageSubsystem storageSubsystem;
    public static double alignkP = .005;
    public static double alignkFF = .05;


    private TestAutoAlign autoAlignCommand;


    public void init () {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(Constants.PinpointConstants.kEncoderResolution, DistanceUnit.MM);
        pinpoint.setOffsets(Constants.PinpointConstants.kPodXOffset,
                Constants.PinpointConstants.kPodYOffset,
                DistanceUnit.INCH);

        pinpoint.setEncoderDirections(Constants.PinpointConstants.kPodXDirection,
                Constants.PinpointConstants.kPodYDirection);

        pinpoint.initialize();
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 12, 12, AngleUnit.DEGREES, 90));
        Localization.setEstimatedPose(pinpoint.getPosition());
        mecanumDriveSubsystem = new MecanumDriveSubsystem(hardwareMap,this, pinpoint);
//        intakeSubsystem = new IntakeSubsystem(this);
//        storageSubsystem = new StorageSubsystem(this) ;

//        intakeCommand = new IntakeCommand(intakeSubsystem, gamepad1);

//        storageCommand = new StorageCommand(storageSubsystem, gamepad1);




        autoAlignCommand = new TestAutoAlign(mecanumDriveSubsystem, gamepad1);

    }

    @Override
    public void loop(){
        pinpoint.update();
        Localization.setPose2d(pinpoint.getPosX(DistanceUnit.INCH), pinpoint.getPosY(DistanceUnit.INCH));

//        intakeCommand.operate(gamepad1);
        mecanumDriveSubsystem.operate(gamepad1, telemetry, Constants.PinpointConstants.redAlliance);
//        storageCommand.operate(gamepad1);
        autoAlignCommand.operate(Constants.PinpointConstants.redAlliance, pinpoint, alignkP, alignkFF);
        telemetry.addData("Align kP: ", alignkP);
        telemetry.addData("Align kFF: ", alignkFF);
        telemetry.update();
    }
    public void stop(){
//        intakeCommand.shutdown();
        mecanumDriveSubsystem.shutdown();
//        storageCommand.shutdown();

    }
}