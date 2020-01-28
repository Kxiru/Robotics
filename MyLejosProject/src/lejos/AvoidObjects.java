import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class AvoidObjects {
	private static float minDistance = (float)0.25;
	public static void main(String[] args) {
		LCD.drawString("Ready to go?", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor ( MotorPort . A );
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor ( MotorPort . B );
		mLeft . setSpeed (500); // 2 Revolutions Per Second ( RPS )
		mRight . setSpeed (500);
		LCD.clear();
		LCD.drawString("Min distance: ",2,2);
		LCD.drawString(Float.toString(minDistance),2,4);
		float[] distances = new float[1];
		float[] onGround = new float[1];
		EV3UltrasonicSensor front = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider distance = front.getDistanceMode();
		EV3ColorSensor bottom = new EV3ColorSensor(SensorPort.S1);
		SampleProvider touch = bottom.getAmbientMode();
		touch.fetchSample(onGround, 0);
		synchroniseMotors(mLeft, mRight);
		while (onGround[0] < 0.1) {
			distance.fetchSample(distances, 0);
			if (distances[0] < minDistance) {
				turnAround(mLeft, mRight);
				synchroniseMotors(mLeft, mRight);
			}
			touch.fetchSample(onGround, 0);
		}
		Sound.beep();
		LCD.clear();
		LCD.drawString("Robot was lifted!",0,2);
		mLeft.stop();
		mRight.stop();
		Button.ENTER.waitForPressAndRelease();
	}
	public static void turnAround(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		Sound.beep();
		mLeft.backward();
		mRight.backward();
		Delay.msDelay(750);
		mLeft.forward();
		Delay.msDelay(300);
	}
	public static void synchroniseMotors(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		mLeft . synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		mLeft . startSynchronization ();
		mLeft . forward ();
		mRight . forward ();
		mLeft . endSynchronization ();
	}
}
