package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.util.GamepadWrapper;

//Author: Randall Delafuente | Created: 2/15/21 | Last Updated: 2/15/21
//Description: Basic Drive Subsystem
public class AlternateDrive {
    public DcMotorEx fr, fl, br, bl;
    public double yMove, xMove, cMove;

    static final double     COUNTS_PER_MOTOR_REV    = 537.6;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.77953;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);


    //Initialize motors and reverse left motor
    public AlternateDrive(HardwareMap hardwareMap){
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        br = hardwareMap.get(DcMotorEx.class, "br");

        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public AlternateDrive(HardwareMap hardwareMap, double LY, double LX, double RX){
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        br = hardwareMap.get(DcMotorEx.class, "br");

        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        yMove = LY;
        xMove = LX;
        cMove = RX;
    }

    public void TeleOpRun(){
        double maxPower = 1.0; //Maximum power for power range
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
    }

    public void setDriveTarget(int LTarget, int RTarget){
        int nFLTarget = (fl.getCurrentPosition() + (int)(LTarget * COUNTS_PER_INCH));
        int nBLTarget = (bl.getCurrentPosition() + (int)(LTarget * COUNTS_PER_INCH));
        int nFRTarget = (fr.getCurrentPosition() + (int)(RTarget * COUNTS_PER_INCH));
        int nBRTarget = (br.getCurrentPosition() + (int)(RTarget * COUNTS_PER_INCH));

        fr.setTargetPosition(nFRTarget);
        fl.setTargetPosition(nFLTarget);
        br.setTargetPosition(nBRTarget);
        bl.setTargetPosition(nBLTarget);

    }

    public void setStrafeTarget(int Target){
        int nFLTarget = (fl.getCurrentPosition() + (int)(Target * COUNTS_PER_INCH));
        int nBLTarget = (bl.getCurrentPosition() + (int)(Target * COUNTS_PER_INCH));
        int nFRTarget = (fr.getCurrentPosition() + (int)(Target * COUNTS_PER_INCH));
        int nBRTarget = (br.getCurrentPosition() + (int)(Target * COUNTS_PER_INCH));

        fr.setTargetPosition(-nFRTarget);
        fl.setTargetPosition(nFLTarget);
        br.setTargetPosition(nBRTarget);
        bl.setTargetPosition(-nBLTarget);
    }

    public void setRunToPos(){
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setMotorPower(double speed){
        fr.setPower(Math.abs(speed));
        fl.setPower(Math.abs(speed));
        br.setPower(Math.abs(speed));
        bl.setPower(Math.abs(speed));
    }
    public void setRunUsingEnc(){
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setStopAndReset(){
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}