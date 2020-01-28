import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;

public class ClapClapCar {
	private static float minNoise = 0.75f;
	public static void main(String[] args) {
		LCD.drawString("Ready to go?", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor ( MotorPort . A );
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor ( MotorPort . B );
		mLeft . setSpeed (500); // 2 Revolutions Per Second ( RPS )
		mRight . setSpeed (500);
		LCD.clear();
		LCD.drawString("Min noise: ",2,2);
		LCD.drawString(Float.toString(minNoise),2,4);
		float[] noise = new float[1];
		NXTSoundSensor ss = new NXTSoundSensor ( SensorPort . S3 );
		SampleProvider sound = ss.getDBAMode ();
		sound.fetchSample(noise, 0);
		while (noise[0] <=minNoise) {
			sound.fetchSample(noise,0);
		}
		Sound.beep();
		synchroniseMotors(mLeft, mRight);
		Button.ENTER.waitForPressAndRelease();
	}
	public static void synchroniseMotors(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		mLeft . synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		mLeft . startSynchronization ();
		mLeft . forward ();
		mRight . forward ();
		mLeft . endSynchronization ();
	}
}
