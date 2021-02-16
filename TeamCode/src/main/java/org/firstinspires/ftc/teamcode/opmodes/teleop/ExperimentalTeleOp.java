package org.firstinspires.ftc.teamcode.opmodes.teleop;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.subsystems.*;

import org.firstinspires.ftc.teamcode.util.GamepadWrapper;

//Author: Randall Delafuente | Created: 02/10/21 | Last Updated: 02/15/21
//Description: Use this TeleOp for experimentation. Things are subject to break here. Proceed with caution.
@TeleOp(name = "Experimental", group = "Testing")
//@Disabled
public class ExperimentalTeleOp extends OpMode {

    //Drive Motors
    private DcMotorEx fl, fr, br, bl;

    //Shooter Motor
    private DcMotorEx shooter;

    //Manipulator Servos
    private Servo lift;
    private Servo flipper;

    private GamepadWrapper joystick = new GamepadWrapper();

    private ElapsedTime time = new ElapsedTime();
    private double velocity;
    private int encoder;
    //Subsystem declaration
    Intake intake;

    //LEDDriver led;
    @Override
    public void init(){
        //Subsystem initialization
        intake = new Intake(hardwareMap);


        fl = hardwareMap.get(DcMotorEx.class, "fl");
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        br = hardwareMap.get(DcMotorEx.class, "br");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        lift = hardwareMap.get(Servo.class, "lift");
        flipper = hardwareMap.get(Servo.class, "flipper");

        MotorConfigurationType motorConfigurationType = shooter.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
        shooter.setMotorType(motorConfigurationType);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void start(){

    }

    @Override
    public void loop() {

        time.reset();

        // db: 0, off: 0.05, gain: 0.95
        //try gain 0.9, 0.82, and 0.7
        double gamepad1LY = joystick.joystick_conditioning(gamepad1.left_stick_y, 0, 0.05, 0.95);
        double gamepad1LX = joystick.joystick_conditioning(gamepad1.left_stick_x, 0, 0.05, 0.95);
        double gamepad1RX = joystick.joystick_conditioning(gamepad1.right_stick_x, 0, 0.05, 0.95);

        //Mecanum values
        double maxPower = 1.0; //Maximum power for power range
        double yMove = gamepad1LY;
        double xMove = gamepad1LX;
        double cMove = gamepad1RX;
        double frontLeftPower ; //Front Left motor power
        double frontRightPower; //Front Right motor power
        double backLeftPower; //Back Left motor power
        double backRightPower; //Back Right motor power

        //Calculating Mecanum power
        frontLeftPower = yMove - xMove - cMove;
        frontRightPower = -yMove - xMove - cMove;
        backLeftPower = yMove + xMove - cMove;
        backRightPower = -yMove + xMove - cMove;

        //Limiting power values from -1 to 1 to conform to setPower() limits
        frontLeftPower = Range.clip(frontLeftPower, -maxPower, maxPower);
        frontRightPower = Range.clip(frontRightPower, -maxPower, maxPower);
        backLeftPower = Range.clip(backLeftPower, -maxPower, maxPower);
        backRightPower = Range.clip(backRightPower, -maxPower, maxPower);

        //Setting power to Mecanum drivetrain
        fl.setPower(-frontLeftPower);
        fr.setPower(-frontRightPower);
        bl.setPower(-backLeftPower);
        br.setPower(-backRightPower);

        //------------------------------------------------------------------------------------------


        shooter.setPower(-gamepad2.left_stick_y);
        velocity = shooter.getVelocity();
        encoder = shooter.getCurrentPosition();
        intake.TeleOpRun();

        if(gamepad2.y)  {lift.setPosition(0);} // up
        if(gamepad2.a)  {lift.setPosition(1);} // down
        if(gamepad2.x)  {flipper.setPosition(0.849);} //shoot
        if(gamepad2.b)  {flipper.setPosition(0.5);} //not shoot
        //Update telemetry
        telemetry.addData("Current Shooter Velocity: ", velocity);
        telemetry.addData("Current Shooter Encoder: ", encoder);
        telemetry.update();
    }

    @Override
    public void stop(){


    }

}
