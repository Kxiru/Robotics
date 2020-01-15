package lejos;

import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class GoCar {

	public static void main(String[] args) {
		Button.ENTER.waitForPressAndRelease();
		
		
		// TODO Auto-generated method stub
		// x4 Import Errors
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor (MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor (MotorPort.B);
		mLeft . setSpeed (720); // 2 Revolutions Per Second ( RPS )
		mRight . setSpeed (720);
		// mLeft . synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		// mLeft . startSynchronization ();
		mLeft.forward ();
		mRight.forward ();
		// mLeft . endSynchronization ();
		// Import Error
		Delay . msDelay (1000);
		// mLeft . startSynchronization ();
		mLeft . stop ();
		// Delay . msDelay (100);
		mRight . stop ();
		// mLeft . endSynchronization ();
		mLeft . close ();
		mRight . close ();
	}

}
