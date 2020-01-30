import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SimpleChap {
	private static float minNoise = 0.75f;
	private static float minDistance = 0.5f;
	public static void main(String[] args) {
		LCD.drawString("Ready to go?", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor ( MotorPort . A );
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor ( MotorPort . B );
		mLeft . setSpeed (500); // 2 Revolutions Per Second ( RPS )
		mRight . setSpeed (500);
		float[] noise = new float[1];
		float[] distances = new float[1];
		NXTSoundSensor ss = new NXTSoundSensor ( SensorPort . S3 );
		SampleProvider sound = ss.getDBAMode ();
		EV3UltrasonicSensor front = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider distance = front.getDistanceMode();
		sound.fetchSample(noise,0);
		LCD.drawString("I am doing nothing",0,2);
		LCD.drawString("until you clap", 0, 3);
		while (noise[0]<=minNoise) {
			sound.fetchSample(noise,0);
		}
		LCD.clear();
		LCD.drawString("Min noise: ",2,1);
		LCD.drawString(Float.toString(minNoise),2,3);
		LCD.drawString("Min distance: ",2,5);
		LCD.drawString(Float.toString(minDistance),2,7);
		synchroniseMotors(mLeft, mRight);
		distance.fetchSample(distances, 0);
		while (distances[0]>=minDistance) {
			sound.fetchSample(noise,0);
			if (noise[0] >=minNoise) {
				turnRight(mLeft,mRight);
				Delay.msDelay(740);
				synchroniseMotors(mLeft, mRight);
			}
			distance.fetchSample(distances, 0);
		}
		Sound.beep();
		stopMotors(mLeft, mRight);
		LCD.clear();
		LCD.drawString("Wall spotted!", 2, 2);
		Button.ENTER.waitForPressAndRelease();
	}
	public static void synchroniseMotors(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		mLeft . synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		mLeft . startSynchronization ();
		mLeft . forward ();
		mRight . forward ();
		mLeft . endSynchronization ();
	}
	public static void stopMotors(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		mLeft . synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		mLeft . startSynchronization ();
		mLeft . stop();
		mRight . stop();
		mLeft . endSynchronization ();
	}
	public static void turnRight(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		mLeft . synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		mLeft . startSynchronization ();
		mLeft . forward ();
		mRight . backward ();
		mLeft . endSynchronization ();
	}
}
