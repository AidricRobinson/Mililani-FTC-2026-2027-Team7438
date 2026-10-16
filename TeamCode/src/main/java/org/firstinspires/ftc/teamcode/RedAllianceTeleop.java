package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated.AutoFlowerIntake;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated.AutoIntake;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated.AutoScore;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated.PanicResetRotation;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Automated.ToggleFlywheels;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual.BrakeCommand;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual.ManualIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual.ManualKickerCommand;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual.ManualShootCommand;
import org.firstinspires.ftc.teamcode.Commands.TeleOp.Manual.PivotCommand;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.PivotSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

@TeleOp(name="Red Teleop")
public class RedAllianceTeleop extends OpMode {
    private GoBildaPinpointDriver pinpoint;
    private Limelight3A limelight;
    private PinpointLocalizer pinpointLocalizer;
    private LimelightLocalizer limelightLocalizer;
    private Localization localization;

    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private PivotSubsystem pivotSubsystem;
    private StorageSubsystem storageSubsystem;
    private ShooterSubsystem shooterSubsystem;

    private AutoFlowerIntake autoFlowerIntake;
    private AutoIntake autoIntake;
    private AutoScore autoScore;
    private PanicResetRotation panicResetRotation;

    private ToggleFlywheels toggleFlywheels;
    private BrakeCommand brakeCommand;
    private PivotCommand pivotCommand;
    private ManualIntakeCommand manualIntakeCommand;
    private ManualShootCommand manualShootCommand;
    private ManualKickerCommand manualKickerCommand;

    private final ElapsedTime elapsedTime = new ElapsedTime();
    private double lastTime;

    private double time;
    private double dt;


    public void init() {

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(Constants.PinpointConstants.kEncoderResolution, DistanceUnit.MM);
        pinpoint.setOffsets(Constants.PinpointConstants.kPodXOffset,
                Constants.PinpointConstants.kPodYOffset,
                DistanceUnit.INCH);

        pinpoint.setEncoderDirections(Constants.PinpointConstants.kPodXDirection,
                Constants.PinpointConstants.kPodYDirection);

        pinpoint.initialize();
        pinpoint.setPosition(Localization.getRobotPose());

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(60);
        limelight.pipelineSwitch(0); //You configure the pipeline in the http://limelight.local:5801/, make sure to press the button on top middle screen, change ID filter
        limelight.start();

        pinpointLocalizer = new PinpointLocalizer(pinpoint);
        limelightLocalizer = new LimelightLocalizer(limelight);
        localization = new Localization(pinpointLocalizer, limelightLocalizer, this, Localization.getRobotPose());

        mecanumDriveSubsystem = new MecanumDriveSubsystem(this.hardwareMap, this, pinpoint);
        intakeSubsystem = new IntakeSubsystem(this);
        pivotSubsystem = new PivotSubsystem(this);
        storageSubsystem = new StorageSubsystem(this);
        shooterSubsystem = new ShooterSubsystem(this);

        autoFlowerIntake = new AutoFlowerIntake(intakeSubsystem, storageSubsystem, pivotSubsystem, gamepad1);
        autoIntake = new AutoIntake(intakeSubsystem, storageSubsystem, gamepad1);
        autoScore = new AutoScore(mecanumDriveSubsystem, storageSubsystem, shooterSubsystem, limelight, gamepad1);
        panicResetRotation = new PanicResetRotation(gamepad1);

        toggleFlywheels = new ToggleFlywheels(shooterSubsystem, gamepad2);
        brakeCommand = new BrakeCommand(mecanumDriveSubsystem, gamepad2);
        pivotCommand = new PivotCommand(pivotSubsystem, gamepad2);
        manualIntakeCommand = new ManualIntakeCommand(intakeSubsystem, gamepad2);
        manualShootCommand = new ManualShootCommand(shooterSubsystem, gamepad2);
        manualKickerCommand = new ManualKickerCommand(storageSubsystem, gamepad2);

        lastTime = 0;
        elapsedTime.startTime();
        time = 0;
        dt = 0;
    }
    public void updatetime() {
        time = elapsedTime.seconds();
        dt = time - lastTime;
        lastTime = time;

        if (dt <= 0 || dt >= 0.2) {
            dt = 0.02;
        }
    }

    @Override
    public void loop() {
        updatetime();
        limelight.getLatestResult();
        pinpoint.update();
        localization.operate(dt);
        limelightLocalizer.update(pinpointLocalizer);
        pinpointLocalizer.update();

        mecanumDriveSubsystem.operate(gamepad1, this.telemetry, Constants.PinpointConstants.redAlliance);
        autoFlowerIntake.operate();
        autoIntake.operate();
        autoScore.operate(Constants.PinpointConstants.redAlliance, dt);
        panicResetRotation.operate(localization);
        toggleFlywheels.operate();
        brakeCommand.operate();
        pivotCommand.operate(gamepad2);
        manualIntakeCommand.operate();
        manualShootCommand.operate(dt);
        manualKickerCommand.operate();
    }

    public void stop() {
        mecanumDriveSubsystem.shutdown();
        intakeSubsystem.shutdown();
        storageSubsystem.shutdown();
        pivotSubsystem.shutdown();
        shooterSubsystem.shutdown();
    }
}
