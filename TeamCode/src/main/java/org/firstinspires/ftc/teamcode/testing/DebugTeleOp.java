package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.subsystems.*;

import org.firstinspires.ftc.teamcode.util.GamepadWrapper;

//Author: Randall Delafuente | Created: 02/10/21 | Last Updated: 02/10/21
//Description: Use this TeleOp for experimentation. Things are subject to break here. Proceed with caution.
@TeleOp(name = "Debug", group = "Testing")
//@Disabled
public class DebugTeleOp extends OpMode {

    public Servo flippyBoi;

    GamepadWrapper joystick = new GamepadWrapper();

    //Subsystem declaration
    Intake intake;

    //LEDDriver led;

    ElapsedTime runtime = new ElapsedTime();


    @Override
    public void init(){

        flippyBoi = hardwareMap.get(Servo.class, "flipper");

    }

    @Override
    public void start(){

    }

    @Override
    public void loop() {



        if(gamepad1.a){flippyBoi.setPosition(0);}
        if (gamepad1.x){flippyBoi.setPosition(0.25);}
        if (gamepad1.y){flippyBoi.setPosition(0.5);} // not
        if (gamepad1.b){flippyBoi.setPosition(0.75);} //shoot
        if (gamepad1.dpad_up){flippyBoi.setPosition(1);}


    }

    @Override
    public void stop(){


    }
        }

