import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class TestWaitStop { 
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
	private BaseRegulatedMotor m;
	EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2) ;
	SampleProvider sp = us.getDistanceMode();
	float[] samples = new float[1];
	public WatcherThread ( BaseRegulatedMotor newMotor) { 
		this.m = newMotor; 
	}
	public void run ( ) {
		while (true) {
			sp.fetchSample(samples, 0) ;
			m.setSpeed(samples[0] <= 0.5 ? 1:720);
			Thread.yield(); // P o l i t e nod . . . .
		}
	}
}
