import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class StopCar {

	public static void main(String[] args) {
		Button.ENTER.waitForPressAndRelease();
		
		
		// TODO Auto-generated method stub
		// x4 Import Errors
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor (MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor (MotorPort.B);
		mLeft . setSpeed (720); // 2 Revolutions Per Second ( RPS )
		mRight . setSpeed (720);
		//mLeft . synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		//mLeft . startSynchronization ();
		mLeft.forward ();
		mRight.forward ();
		// mLeft . endSynchronization ();
		// Import Error
		Button.ENTER.waitForPressAndRelease();
		// mLeft . startSynchronization ();
		mLeft . stop ();
		// Delay . msDelay (100);
		mRight . stop ();
		// mLeft . endSynchronization ();
		int distance = mLeft.getTachoCount();
		mLeft . close ();
		mRight . close ();
		Button.ENTER.waitForPressAndRelease();
		LCD.drawInt(distance,2,2);
//		LCD.drawInt(mLeft.getTachoCount(),0,0);
		Button.ENTER.waitForPressAndRelease();
		
	}

}