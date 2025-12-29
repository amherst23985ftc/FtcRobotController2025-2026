package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;

@Autonomous(name="AutoTest", group="test")
public class autoTest extends LinearOpMode {

    private DriveSubsystem drive;

    @Override
    public void runOpMode() {
        drive = new DriveSubsystem(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        // Start at origin
        drive.setPoseEstimate(new Pose2d());

        // Move forward 24 inches
        Trajectory forward = drive.trajectoryBuilder(new Pose2d())
                .forward(24)
                .build();
        drive.followTrajectory(forward);

        sleep(500);

        // Move backward 24 inches
        Trajectory backward = drive.trajectoryBuilder(drive.getPoseEstimate())
                .back(24)
                .build();
        drive.followTrajectory(backward);
    }
}
