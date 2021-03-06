/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Set;

/**
 * This OpMode uses the JoeBot hardware class to define the devices on the robot.
 * All device access is managed through the HardwareJoeBot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a Tank Drive style Teleop for the 2015 JoeBot
 */

@TeleOp(name="Servo-Test", group="Test")

public class Opmode_ServoTest extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJoeBot robot = new HardwareJoeBot();     // Use a JoeBot's hardware

    boolean bCurrStateR = false;
    boolean bPrevStateR = false;
    boolean bCurrStateL = false;
    boolean bPrevStateL = false;
    boolean bLSRVout = false;
    boolean bRSRVout = false;
    boolean bRightBumper = false;
    boolean bLeftBumper = false;
    boolean bLeftBumperCurrState    = false;
    boolean bLeftBumperPrevState    = false;
    boolean bRightBumperCurrState   = false;
    boolean bRightBumperPrevState   = false;


    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;


        //double max;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         *
         * Servos should be initiated to their "IN" positions as Part of HardwareJoeBot class
         *
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Ethan");    //
        telemetry.addLine();
        telemetry.addData("Left Servo Position:", "%5.2f", robot.LEFT_SERVO_IN);
        telemetry.addData("Right Servo Position:", "%5.2f", robot.RIGHT_SERVO_IN);
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            // check the status of the b button on gamepad1.
            bCurrStateR = gamepad1.b;

            // check for "b" button state transitions.
            if ((bCurrStateR == true) && (bCurrStateR != bPrevStateR)) {

                // button is transitioning to a pressed state. So Toggle ServoOut variable
                bRSRVout = !bRSRVout;
            }

            // update previous state variable.
            bPrevStateR = bCurrStateR;


            // check the status of the "X" button on gamepad1
            bCurrStateL = gamepad1.x;

            // Check for "x" button state transition
            if ((bCurrStateL == true) && (bCurrStateL != bPrevStateL)) {

                // button is tranitioning to a pressed state. Toggle ServoOut variable
                bLSRVout = !bLSRVout;
            }
            // update previous state variable
            bPrevStateL = bCurrStateL;


            // If bRSRVout, extend Right button Pusher, else bring it in.
            if (bRSRVout) {
                robot.srv_right.setPosition(robot.RIGHT_SERVO_OUT);
                telemetry.addData("right Servo Position:", "%5.2f", robot.RIGHT_SERVO_OUT);
            } else {
                robot.srv_right.setPosition(robot.RIGHT_SERVO_IN);
                telemetry.addData("Right Servo Position:", "%5.2f", robot.RIGHT_SERVO_IN);
            }

            // If bLSRVout, extend Left button Pusher, else bring it in.
            if (bLSRVout) {
                robot.srv_left.setPosition(robot.LEFT_SERVO_OUT);
                telemetry.addData("Left Servo Position:", "%5.2f", robot.LEFT_SERVO_OUT);
            } else {
                robot.srv_left.setPosition(robot.LEFT_SERVO_IN);
                telemetry.addData("Left Servo Position:", "%5.2f", robot.LEFT_SERVO_IN);
            }

            // In this section, we'll look for bumpers, then cycle the servos appropriately.

            // Check for leftBumper Status
            bLeftBumperCurrState = gamepad1.left_bumper;

            // Check for LeftBumber State Transition
            if ((bLeftBumperCurrState == true) && (bLeftBumperCurrState != bLeftBumperPrevState)) {

                //Button is transitioning to a pressed state. Cycle Servos
                robot.srv_left.setPosition(robot.LEFT_SERVO_OUT);
                robot.waitForTick(120);
                robot.srv_left.setPosition(robot.LEFT_SERVO_IN);
            }

            bLeftBumperPrevState = bLeftBumperCurrState;

            // Check for leftBumper Status
            bRightBumperCurrState = gamepad1.right_bumper;

            // Check for LeftBumber State Transition
            if ((bRightBumperCurrState == true) && (bRightBumperCurrState != bRightBumperPrevState)) {

                //Button is transitioning to a pressed state. Cycle Servos
                robot.srv_right.setPosition(robot.RIGHT_SERVO_OUT);
                robot.waitForTick(120);
                robot.srv_right.setPosition(robot.RIGHT_SERVO_IN);
            }

            bRightBumperPrevState = bRightBumperCurrState;




            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);


        }


    }
}
