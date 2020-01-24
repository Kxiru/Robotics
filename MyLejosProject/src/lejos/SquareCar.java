import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class SquareCar {
	static BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor ( MotorPort . A );
	static BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor ( MotorPort . B );
	public static void main(String[] args) { 
		Button.ENTER.waitForPressAndRelease();
		mLeft.setSpeed(500);
		mRight.setSpeed(500);
		for (int i=0; i<4; i++) {
			syncForward();
			Delay.msDelay(1000);
			spin();
			Delay.msDelay(750);
			stopMotors();
		}
	}
	public static void syncForward() {
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
	}
	public static void spin() {
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.backward();
		mLeft.endSynchronization();
	}
	public static void stopMotors() {
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.stop();
		mRight.stop();
		mLeft.endSynchronization();
	}
}
