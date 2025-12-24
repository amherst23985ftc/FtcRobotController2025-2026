package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "colorDetectorTest")

public class colorDetectorTest extends LinearOpMode {

    private ColorSensor colorDetector;

    private void hardwareMapping() {
        colorDetector = hardwareMap.get(ColorSensor.class, "colorDetector");
    }

    /*
    private String colorDetection() {
        int red = colorDetector.red();
        int green = colorDetector.green();
        int blue = colorDetector.blue();

        // Prevent division issues in darkness
        int total = red + green + blue;
        if (total < 50) {
            return "unknown";
        }

        double redRatio = (double) red / total;
        double greenRatio = (double) green / total;
        double blueRatio = (double) blue / total;

        // Green: green dominates
        if (greenRatio > 0.45 && greenRatio > redRatio && greenRatio > blueRatio) {
            return "green";
        }

        // Purple: red + blue dominate over green
        if ((redRatio + blueRatio) > 0.65 && greenRatio < 0.30) {
            return "purple";
        }

        return "unknown";

    }
    */
    private String colorDetection() {
        int red = colorDetector.red();
        int green = colorDetector.green();
        int blue = colorDetector.blue();

        int total = red + green + blue;
        if (total < 60) {
            return "unknown";
        }

        double redRatio = (double) red / total;
        double greenRatio = (double) green / total;
        double blueRatio = (double) blue / total;

        // Saturation check (reject white / gray)
        double max = Math.max(redRatio, Math.max(greenRatio, blueRatio));
        double min = Math.min(redRatio, Math.min(greenRatio, blueRatio));
        double saturation = max - min;

        if (saturation < 0.12) {
            return "unknown";
        }

        // GREEN detection
        if (greenRatio > 0.40 && greenRatio > redRatio && greenRatio > blueRatio) {
            return "green";
        }

        // PURPLE detection (handles light purple)
        boolean redBlueClose = Math.abs(redRatio - blueRatio) < 0.12;
        boolean redBlueAboveGreen = redRatio > greenRatio * 0.9 && blueRatio > greenRatio * 0.9;

        if (redBlueClose && redBlueAboveGreen) {
            return "purple";
        }

        return "unknown";
    }


    private void printThings() {
        telemetry.addData("R", colorDetector.red());
        telemetry.addData("G", colorDetector.green());
        telemetry.addData("B", colorDetector.blue());

        telemetry.addData("Color: ", colorDetection());

        telemetry.update();
    }


    private void initializeAndSetUp() {
        colorDetector.enableLed(true);
        hardwareMapping();
    }


    @Override
    public void runOpMode() throws InterruptedException {
        initializeAndSetUp();
        waitForStart();
        while (opModeIsActive()) {
            printThings();
        }
    }

}
