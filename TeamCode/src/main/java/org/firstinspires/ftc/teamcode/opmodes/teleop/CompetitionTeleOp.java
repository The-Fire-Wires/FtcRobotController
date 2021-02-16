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

import org.firstinspires.ftc.teamcode.subsystems.drive.AlternateDrive;
import org.firstinspires.ftc.teamcode.util.GamepadWrapper;

//Author: Randall Delafuente | Created: 02/15/21 | Last Updated: 02/15/21
//Description: Final TeleOp for use in competition!
@TeleOp(name = "Competition", group = "Testing")
//@Disabled
public class CompetitionTeleOp extends OpMode {

    private GamepadWrapper joystick = new GamepadWrapper();
    private ElapsedTime time = new ElapsedTime();

    double gamepad1LY = joystick.joystick_conditioning(gamepad1.left_stick_y, 0, 0.05, 0.95);
    double gamepad1LX = joystick.joystick_conditioning(gamepad1.left_stick_x, 0, 0.05, 0.95);
    double gamepad1RX = joystick.joystick_conditioning(gamepad1.right_stick_x, 0, 0.05, 0.95);

    //Subsystem declaration
    Intake intake;
    AlternateDrive drive;
    Shooter cannon;

    //LEDDriver led;
    @Override
    public void init(){
        //Subsystem initialization
        drive = new AlternateDrive(hardwareMap, gamepad1LY, gamepad1LX, gamepad1RX);
        intake = new Intake(hardwareMap, gamepad1.left_trigger, gamepad1.right_trigger);
        cannon = new Shooter(hardwareMap, gamepad2.left_stick_y, gamepad2.y, gamepad2.a, gamepad2.x, gamepad2.b);
    }

    @Override
    public void start(){

    }

    @Override
    public void loop() {

        time.reset();

        drive.TeleOpRun();
        intake.TeleOpRun();
        cannon.TeleOpRun();

    }

    @Override
    public void stop(){


    }

}
