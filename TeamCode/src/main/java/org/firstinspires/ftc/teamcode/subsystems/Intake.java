package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.GamepadWrapper;

//Author: Randall Delafuente | Created: 2/10/21 | Last Updated: 2/15/21
//Description: Intake Subsystem
public class Intake {
    private CRServo intake;
    private DcMotorEx conveyor;
    private double LT, RT;
    GamepadWrapper trigger = new GamepadWrapper();

    //Initialize motors and reverse left motor
    public Intake(HardwareMap hardwareMap){
        intake = hardwareMap.get(CRServo.class, "intake");
        conveyor = hardwareMap.get(DcMotorEx.class, "conveyor");
    }

    public Intake(HardwareMap hardwareMap, double gLTrigger, double gRTrigger){
        intake = hardwareMap.get(CRServo.class, "intake");
        conveyor = hardwareMap.get(DcMotorEx.class, "conveyor");
        LT = gLTrigger;
        RT = gRTrigger;
    }

    //Set power to intake subsytem
    public void TeleOpRun(){
        //Power logic
        if (LT > 0)
        intake.setPower(LT);
        conveyor.setPower(LT);
        if (RT > 0)
        intake.setPower(-RT);
        conveyor.setPower(-RT);
        if (LT == 0 && RT == 0)
        intake.setPower(0);
        conveyor.setPower(0);
    }

    public void setAutoPower(double speed){
        intake.setPower(speed);
    }
}