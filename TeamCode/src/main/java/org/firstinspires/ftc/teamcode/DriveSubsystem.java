package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem extends MecanumDrive {

    // Motors
    public DcMotorEx frontLeft, frontRight, backLeft, backRight;

    public DriveSubsystem(HardwareMap hardwareMap) {
        super(DriveConstants.kV, DriveConstants.kA, DriveConstants.kStatic, DriveConstants.TRACK_WIDTH);

        // Initialize motors
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        // Reverse directions if needed
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);

        // Set motors to run using encoders
        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    // Implement abstract methods
    @Override
    public void setMotorPowers(double fl, double bl, double br, double fr) {
        frontLeft.setPower(fl);
        backLeft.setPower(bl);
        backRight.setPower(br);
        frontRight.setPower(fr);
    }

    @Override
    public double[] getWheelPositions() {
        return new double[] {
                DriveConstants.encoderTicksToInches(frontLeft.getCurrentPosition()),
                DriveConstants.encoderTicksToInches(backLeft.getCurrentPosition()),
                DriveConstants.encoderTicksToInches(backRight.getCurrentPosition()),
                DriveConstants.encoderTicksToInches(frontRight.getCurrentPosition())
        };
    }

    @Override
    public void setMotorPowers(double[] powers) {
        setMotorPowers(powers[0], powers[1], powers[2], powers[3]);
    }
}
