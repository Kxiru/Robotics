package lejos;

import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class CarRotate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Button.ENTER.waitForPressAndRelease();
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor ( MotorPort . A );
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor ( MotorPort . B );
		//DifferentialPilot pilot = new DifferentialPilot(2.1f, 4.4f, mLeft, mRight, true);
		// Tell JVM what the left motor is synchronized with .
		mLeft . synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		mLeft . setSpeed (360); // 2 Revolutions Per Second ( RPS )
		mRight . setSpeed (360);
		// Do a for loop here to run FOUR times
		
			for (int i = 0; i<4; i++) {
			//pilot . rotate (90); // one motor turns to go around a corner
			// start a synchronized edge of the square
			mLeft . rotate (355);
			
			mLeft . startSynchronization ();
			// these rotates will not begin until we end the synchronization
			
			mLeft . rotate (540);
			mRight . rotate (540);
			// actually begin both motors rotating at exactly the same time
			
			mLeft . endSynchronization ();
			
			mLeft . waitComplete ();
			mRight . waitComplete (); // wait for both motors to finish turning
			}
			
		mLeft . close ();
		mRight . close ();
	}

}
