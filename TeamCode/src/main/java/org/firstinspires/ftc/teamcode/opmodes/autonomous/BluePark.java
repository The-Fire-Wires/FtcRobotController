package org.firstinspires.ftc.teamcode.opmodes.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.drive.AlternateDrive;

@Autonomous(name = "BluePark", group = "Testing")
//@Disabled
public class BluePark extends LinearOpMode {

    AlternateDrive drive;
    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        //Step 0: Initialize and get into position
        drive = new AlternateDrive(hardwareMap);

        waitForStart();
        //Step 1: Literally Park
        encoderDrive(0.6, 65,65,10.0);

        drive.fr.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.br.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    // Target in inches
    public void encoderDrive(double speed, int leftTarget, int rightTarget, double timeoutS){

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            drive.setDriveTarget(leftTarget, rightTarget);

            // Turn On RUN_TO_POSITION
            drive.setRunUsingEnc();
            drive.setRunToPos();

            // reset the timeout time and start motion.
            runtime.reset();
            drive.setMotorPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (drive.fr.isBusy() && drive.fl.isBusy() && drive.br.isBusy() && drive.bl.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Projected Target", "Running to %7d, :%7d", leftTarget, rightTarget);
                telemetry.addData("Current Position", "Running at %7d, :%7d, :%7d, :%7d", drive.fl.getCurrentPosition(), drive.bl.getCurrentPosition(),
                        drive.fr.getCurrentPosition(), drive.br.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            drive.setMotorPower(0.0);

            // Turn off RUN_TO_POSITION
            drive.setRunUsingEnc();
            drive.setStopAndReset();

            //  sleep(250);   // optional pause after each move
        }

    }

}
