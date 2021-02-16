package org.firstinspires.ftc.teamcode.opmodes.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.drive.AlternateDrive;

//Author: Randall Delafuente | Created: 2/15/21 | Last Updated: 2/15/21
//Description: Experimental Autonomous, Red Side
@Autonomous(name = "RedEx", group = "Testing")
//@Disabled
public class RedExperimental extends LinearOpMode {

    AlternateDrive drive;
    Shooter shooter;
    Intake intake;
    ElapsedTime runtime = new ElapsedTime();
    private int path = 2;

    @Override
    public void runOpMode() throws InterruptedException {
        //Step 0: Initialize and get into position
        drive = new AlternateDrive(hardwareMap);
        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);

        waitForStart();
        //Step 1:
        while(opModeIsActive()){
            switch(path){
                case 1: encoderDrive(0.7, 95,95,10.0);
                        wobbleGoalDrop();
                        encoderDrive(0.3, -40, -40,4.0);
                        encoderDrive(0.3,-4,4,3.0);
                        shootThree();
                        encoderDrive(0.5, 4,4,4.0);
                        drive.fr.setDirection(DcMotorSimple.Direction.FORWARD);
                        drive.br.setDirection(DcMotorSimple.Direction.FORWARD);
                        sleep(30000);
                        break;

                case 2: encoderDrive(0.6, 77,77,10.0);
                        strafeDrive(0.4,-20,5.0);
                        wobbleGoalDrop();
                        encoderDrive(0.3, -24, -24,4.0);
                        shootThree();
                        encoderDrive(0.5, 16,16,4.0);
                        drive.fr.setDirection(DcMotorSimple.Direction.FORWARD);
                        drive.br.setDirection(DcMotorSimple.Direction.FORWARD);
                        sleep(30000);
                        break;


                case 3: encoderDrive(0.6, 65,65,10.0);
                        wobbleGoalDrop();
                        encoderDrive(0.3, -4, -4,4.0);
                        encoderDrive(0.3,-4,4,3.0);
                        shootThree();
                        strafeDrive(0.4,-12,5.0);
                        encoderDrive(0.5, 11,11,4.0);
                        drive.fr.setDirection(DcMotorSimple.Direction.FORWARD);
                        drive.br.setDirection(DcMotorSimple.Direction.FORWARD);
                        sleep(30000);
                        break;
            }
        }
    }
    // Target in inches
    public void encoderDrive(double speed, int leftTarget, int rightTarget, double timeoutS){

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            drive.setDriveTarget(leftTarget, rightTarget);

            // Turn On RUN_TO_POSITION
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

            //  sleep(250);   // optional pause after each move
        }

    }
    public void strafeDrive(double speed, int target, double timeoutS){

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            drive.setStrafeTarget(target);

            // Turn On RUN_TO_POSITION
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
                telemetry.addData("Projected Target", "Running to %7d", target);
                telemetry.addData("Current Position", "Running at %7d, :%7d, :%7d, :%7d", drive.fl.getCurrentPosition(), drive.bl.getCurrentPosition(),
                        drive.fr.getCurrentPosition(), drive.br.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            drive.setMotorPower(0.0);

            // Turn off RUN_TO_POSITION
            drive.setRunUsingEnc();

            //  sleep(250);   // optional pause after each move
        }

    }
    public void wobbleGoalDrop(){
        intake.setAutoPower(-1.0);
        sleep(2000);
        intake.setAutoPower(0.0);
    }
    public void shootOne(){
        shooter.flipper.setPosition(.5);
        shooter.shooter.setVelocity(2400);
        sleep(2000);
        shooter.flipper.setPosition(.86);
        sleep(1500);
        shooter.shooter.setVelocity(0);
        shooter.flipper.setPosition(.5);
    }
    public void shootThree(){
        shooter.flipper.setPosition(.5);
        shooter.shooter.setVelocity(2800);
        sleep(3000);
        shooter.flipper.setPosition(.9);
        sleep(1500);
        shooter.flipper.setPosition(.6);
        sleep(1500);
        shooter.flipper.setPosition(.9);
        sleep(1500);
        shooter.flipper.setPosition(.6);
        sleep(1500);
        shooter.flipper.setPosition(.9);
        sleep(1500);
        shooter.flipper.setPosition(.6);
        sleep(1500);
        shooter.shooter.setVelocity(0);
        shooter.flipper.setPosition(.5);
    }
}
