package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.util.GamepadWrapper;

//Author: Randall Delafuente | Created: 2/15/21 | Last Updated: 2/15/21
//Description: Shooter Subsystem
public class Shooter {
    public Servo flipper, lift;
    public DcMotorEx shooter;
    private float joystick;
    private boolean y, a, x, b;
    private final double shootingSpeed, flipperIn, flipperOut, liftUp, liftDown;
    private boolean flipperPos;
    GamepadWrapper trigger = new GamepadWrapper();

    //Initialize motors and servos (AUTO)
    public Shooter(HardwareMap hardwareMap){
        flipper = hardwareMap.get(Servo.class, "flipper");
        lift = hardwareMap.get(Servo.class, "lift");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        MotorConfigurationType motorConfigurationType = shooter.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
        shooter.setMotorType(motorConfigurationType);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shootingSpeed = 1;
        flipperIn = .849;
        flipperOut = .5;
        liftUp = 0;
        liftDown = 1;
    }

    //Initialize motors and servos (TeleOp)
    public Shooter(HardwareMap hardwareMap, float gJoystick, boolean gY, boolean gA, boolean gX, boolean gB){
        flipper = hardwareMap.get(Servo.class, "flipper");
        lift = hardwareMap.get(Servo.class, "lift");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        MotorConfigurationType motorConfigurationType = shooter.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
        shooter.setMotorType(motorConfigurationType);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        joystick = gJoystick;
        y = gY;
        a = gA;
        x = gX;
        b = gB;
        shootingSpeed = 2200;
        flipperIn = .86;
        flipperOut = .6;
        liftUp = 0;
        liftDown = 1;
        flipperPos = true;
    }

    public void TeleOpRun(){

        if(y && !flipperStatus())  {lift.setPosition(liftUp);} // up
        if(a && !flipperStatus())  {lift.setPosition(liftDown);} // down
        if(x)  {flipper.setPosition(flipperIn); flipperPos = true;} //shoot
        if(b)  {flipper.setPosition(flipperOut); flipperPos = false;} //not shoot
        shooter.setPower(-joystick);
    }

    //True = In, False = Out
    public boolean flipperStatus(){
        return flipperPos;
    }
    //Start with the lift up and flipper in.
    public void autoStart(){
        flipper.setPosition(flipperIn);
        lift.setPosition(liftUp);
    }

}