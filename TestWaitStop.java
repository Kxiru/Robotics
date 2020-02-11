import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

public class TestWaitStop { 
	final static int TOTAL = 1440;
	public static void main ( String [] args ) {
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor ( MotorPort.A) ;
		Thread watcher = new WatcherThread (mL) ;
		watcher.setDaemon(true); watcher.start(); mL.rotate(TOTAL);
		LCD.drawString("Finished " + Motor.A.getTachoCount() , 2 , 4 ) ;
		Button.ENTER.waitForPressAndRelease ( ) ;
		} 
}
class WatcherThread extends Thread {
	private BaseRegulatedMotor m;
	public WatcherThread ( BaseRegulatedMotor newMotor) { 
		this.m = newMotor; 
	}
	public void run ( ) {
		EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S2) ;
		SampleProvider sp = ts.getTouchMode();
		float[] samples = new float[1];
		while (true) {
		sp.fetchSample(samples, 0) ;
		m.setSpeed(samples[0] > 0.5 ? 1:720);
		Thread.yield(); // P o l i t e nod . . . .
		}
	}
}
