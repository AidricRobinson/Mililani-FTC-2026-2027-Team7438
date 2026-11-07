package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual.ManualSpindexerCommand;
import org.firstinspires.ftc.teamcode.Commands.Test.FullShooterCommand;
import org.firstinspires.ftc.teamcode.Commands.Test.TestAutoAlign;
import org.firstinspires.ftc.teamcode.Commands.Test.TestFlowerArm;
import org.firstinspires.ftc.teamcode.Commands.Test.TestIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.Test.TestStorageCommand;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;


@TeleOp(name="TEST ONLY")
@Config
public class TestTeleOp extends OpMode {

    GoBildaPinpointDriver pinpoint;



    //declaring subsystems and commands here
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private PinpointLocalizer pinpointLocalizer;
    private PivotSubsystem pivotSubsystem;

//    private IntakeCommand intakeCommand;
//    private StorageCommand storageCommand;

    private IntakeSubsystem intakeSubsystem;
    private TestIntakeCommand testIntakeCommand;
    private StorageSubsystem storageSubsystem;
//    private ManualSpindexerCommand manualSpindexerCommand;


    private ShooterSubsystem shooterSubsystem;
    private FullShooterCommand fullShooterCommand;
    private TestStorageCommand testStorageCommand;
    private TestFlowerArm testFlowerArm;
    public static double alignkP = .02125;
    public static double alignkFF = .05;


//    private TestAutoAlign autoAlignCommand;




    public void init () {

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(Constants.PinpointConstants.kEncoderResolution, DistanceUnit.MM);
        pinpoint.setOffsets(Constants.PinpointConstants.kPodXOffset,
                Constants.PinpointConstants.kPodYOffset,
                DistanceUnit.INCH);

        pinpoint.setEncoderDirections(Constants.PinpointConstants.kPodXDirection,
                Constants.PinpointConstants.kPodYDirection);
//

        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 12, 12, AngleUnit.DEGREES, 0));
//        Localization.setEstimatedPose(pinpointLocalizer.getPose());
        pinpoint.initialize();

        pinpointLocalizer = new PinpointLocalizer(pinpoint);

        mecanumDriveSubsystem = new MecanumDriveSubsystem(hardwareMap,this);
        pivotSubsystem = new PivotSubsystem(this);
        intakeSubsystem = new IntakeSubsystem(this);
        testIntakeCommand = new TestIntakeCommand(intakeSubsystem, gamepad2);

        storageSubsystem = new StorageSubsystem(this);
//        manualSpindexerCommand = new ManualSpindexerCommand(storageSubsystem, gamepad2);

        shooterSubsystem = new ShooterSubsystem(this);
        fullShooterCommand = new FullShooterCommand(shooterSubsystem, gamepad2);

        testStorageCommand = new TestStorageCommand(storageSubsystem, gamepad2);
        testFlowerArm = new TestFlowerArm(pivotSubsystem, gamepad2);
//        storageSubsystem = new StorageSubsystem(this) ;

//        intakeCommand = new IntakeCommand(intakeSubsystem, gamepad1);

//        storageCommand = new StorageCommand(storageSubsystem, gamepad1);




//        autoAlignCommand = new TestAutoAlign(mecanumDriveSubsystem, gamepad1);

    }

    @Override
    public void loop(){
        pinpoint.update();
        pinpointLocalizer.update();

//        Localization.setPose2d(pinpointLocalizer.getX(), pinpointLocalizer.getY());
        testIntakeCommand.operate(this.telemetry);
        fullShooterCommand.operate();
        testStorageCommand.operate();
        testFlowerArm.operate();
//        intakeCommand.operate(gamepad1);
        mecanumDriveSubsystem.operate(gamepad1, telemetry, Constants.PinpointConstants.redAlliance, pinpointLocalizer);
//        storageCommand.operate(gamepad1);
//        autoAlignCommand.operate(Constants.PinpointConstants.redAlliance, pinpointLocalizer, alignkP, alignkFF);
//        telemetry.addData("Align kP: ", alignkP);
//        telemetry.addData("Align kFF: ", alignkFF);
        storageSubsystem.update();
        shooterSubsystem.update();
        intakeSubsystem.update();

        telemetry.addData("AAAAAAAAAAAAAAAA: ", pinpointLocalizer.getHeading());

        telemetry.update();
    }
    public void stop(){
        intakeSubsystem.shutdown();
//        manualSpindexerCommand.shutdown();
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
//        intakeCommand.shutdown();
        mecanumDriveSubsystem.shutdown();
//        storageCommand.shutdown();

    }
}