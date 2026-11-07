package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Commands.Autonomous.AutoMoveLeft;
import org.firstinspires.ftc.teamcode.Commands.Autonomous.AutoTurn;
import org.firstinspires.ftc.teamcode.Commands.Autonomous.AutonomousShoot;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.StorageSubsystem;

import java.sql.Time;

@Autonomous(name="Red Score and Park", group = "Linear OpMode")
public class RedScoreAndPark extends LinearOpMode {
    private ShooterSubsystem shooterSubsystem;
    private StorageSubsystem storageSubsystem;
    private MecanumDriveSubsystem mecanumDriveSubsystem;
    private GoBildaPinpointDriver pinpoint;
    private PinpointLocalizer pinpointLocalizer;

    private AutoMoveLeft autoMoveLeft;
    private AutoTurn autoTurn;
    private AutonomousShoot autonomousShoot;
    private ElapsedTime elapsedTime;

    @Override
    public void runOpMode() {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(Constants.PinpointConstants.kEncoderResolution, DistanceUnit.MM);
        pinpoint.setOffsets(Constants.PinpointConstants.kPodXOffset,
                Constants.PinpointConstants.kPodYOffset,
                DistanceUnit.INCH);

        pinpoint.setEncoderDirections(Constants.PinpointConstants.kPodXDirection,
                Constants.PinpointConstants.kPodYDirection);
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 90));
        pinpoint.initialize();

        pinpointLocalizer = new PinpointLocalizer(pinpoint);

        mecanumDriveSubsystem = new MecanumDriveSubsystem(hardwareMap, this);
        shooterSubsystem = new ShooterSubsystem(this);
        storageSubsystem = new StorageSubsystem(this);

        autoMoveLeft = new AutoMoveLeft(mecanumDriveSubsystem, this, elapsedTime);
        autoTurn = new AutoTurn(mecanumDriveSubsystem, pinpointLocalizer, this);
        autonomousShoot = new AutonomousShoot(this,
                shooterSubsystem, storageSubsystem, elapsedTime);


        waitForStart();
        elapsedTime.startTime();
        autonomousShoot.operate();
        shooterSubsystem.shutdown();
        storageSubsystem.shutdown();
        autoMoveLeft.operate(3);
        autoTurn.operate(0);
        autoMoveLeft.operate(30);



    }


}
