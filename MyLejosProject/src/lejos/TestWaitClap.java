import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;

public class TestWaitClap { 
	final static int TOTAL = 5000;
	public static void main ( String [] args ) {
		Button.ENTER.waitForPressAndRelease();
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor ( MotorPort.A) ;
		Thread watcher = new WatcherThread (mL) ;
		watcher.setDaemon(true); watcher.start(); mL.rotate(TOTAL);
		LCD.drawString("Finished " + mL.getTachoCount() , 2 , 4 ) ;
		Button.ENTER.waitForPressAndRelease ( ) ;
		} 
}
class WatcherThread extends Thread {
	private static float minNoise = 0.75f;
	private BaseRegulatedMotor m;
	NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S3) ;
	SampleProvider sound = ss.getDBAMode ();
	float[] noise = new float[1];
	public WatcherThread ( BaseRegulatedMotor newMotor) { 
		this.m = newMotor; 
	}
	public void run ( ) {
		while (true) {
			sound.fetchSample(noise,0);
			m.setSpeed(noise[0] <= minNoise ? 1:720);
			Thread.yield(); // P o l i t e nod . . . .
		}
	}
}
