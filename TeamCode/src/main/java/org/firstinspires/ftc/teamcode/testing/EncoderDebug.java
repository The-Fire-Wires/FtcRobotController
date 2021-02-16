package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "EncoderDebug", group = "Testing")
//@Disabled

public class EncoderDebug extends LinearOpMode {

    private DcMotorEx fr, fl, br, bl;
    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        fr = hardwareMap.get(DcMotorEx.class, "fr");
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        br = hardwareMap.get(DcMotorEx.class, "br");
        bl = hardwareMap.get(DcMotorEx.class, "bl");


        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Encoders:", "Starting at %7d :%7d :%7d :%7d",
                fr.getCurrentPosition(),
                fl.getCurrentPosition(),
                br.getCurrentPosition(),
                bl.getCurrentPosition());

        telemetry.update();

        waitForStart();

        individualDrive(fr, 0.3, 4000, 4.0);
        individualDrive(fl, 0.3, 4000, 4.0);
        individualDrive(br, 0.3, 4000, 4.0);
        individualDrive(bl, 0.3, 4000, 4.0);



    }

    public void individualDrive(DcMotorEx motor, double speed, int eValue, double timeoutS){

        if(opModeIsActive()){
            motor.setTargetPosition(eValue);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            runtime.reset();
            motor.setPower(Math.abs(speed));
            while(opModeIsActive() && runtime.seconds() < timeoutS && motor.isBusy()){
                telemetry.addData("Projected Target", "Running to %7d", eValue);
                telemetry.addData("Current Position", "Running at %7d", motor.getCurrentPosition());
                telemetry.update();

            }
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(1000);
        }

    }

    public void encoderDrive(double speed, int frE, int flE, int brE, int blE, double timeoutS){

            // Ensure that the opmode is still active
            if (opModeIsActive()) {

                // Determine new target position, and pass to motor controller
                fr.setTargetPosition(frE);
                fl.setTargetPosition(flE);
                br.setTargetPosition(brE);
                bl.setTargetPosition(blE);

                // Turn On RUN_TO_POSITION
                fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // reset the timeout time and start motion.
                runtime.reset();

                fr.setPower(Math.abs(speed));
                fl.setPower(Math.abs(speed));
                br.setPower(Math.abs(speed));
                bl.setPower(Math.abs(speed));

                // keep looping while we are still active, and there is time left, and both motors are running.
                // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
                // its target position, the motion will stop.  This is "safer" in the event that the robot will
                // always end the motion as soon as possible.
                // However, if you require that BOTH motors have finished their moves before the robot continues
                // onto the next step, use (isBusy() || isBusy()) in the loop test.
                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (fr.isBusy() && fl.isBusy() && br.isBusy() && bl.isBusy())) {

                    // Display it for the driver.
                    telemetry.addData("Projected Target", "Running to %7d :%7d :%7d :%7d", frE, flE, brE, blE);
                    telemetry.addData("Current Position", "Running at %7d :%7d :%7d :%7d",
                            fr.getCurrentPosition(), fl.getCurrentPosition(),
                            br.getCurrentPosition(), bl.getCurrentPosition());
                    telemetry.update();
                }

                // Stop all motion;
                fr.setPower(Math.abs(0));
                fl.setPower(Math.abs(0));
                br.setPower(Math.abs(0));
                bl.setPower(Math.abs(0));

                // Turn off RUN_TO_POSITION
                fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                //  sleep(250);   // optional pause after each move
            }

        }

    }


