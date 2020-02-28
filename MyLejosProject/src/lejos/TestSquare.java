import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class TestSquare {
	final static float wheelDiameter = 36; //diameter in mm of wheel
	final static float axleLength = 150; //distance of your two driven wheels
	final static float angularSpeed = 20; //how fast around corners (degrees/sec)
	final static float linearSpeed = 50; //how fast travel straight line
	public static void main(String[] args) {
		boolean lastTurn = false;
		int turnCount = 1;
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, wheelDiameter).offset(-axleLength/2);
		Wheel wRight = WheeledChassis.modelWheel(mRight, wheelDiameter).offset(axleLength/2);
		Chassis chassis = new WheeledChassis(new Wheel[] {wRight, wLeft}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(linearSpeed);
		pilot.setAngularSpeed(angularSpeed);
		EV3ColorSensor down = new EV3ColorSensor(SensorPort.S1);
		SampleProvider color = down.getColorIDMode();
		float[] colors = new float[color.sampleSize()];
		Button.ENTER.waitForPressAndRelease();
		int travel = 10;
		while(Button.ESCAPE.isUp()) {
			color.fetchSample(colors, 0);
			LCD.drawString(Float.toString(colors[0]), 2, 2);
			if (colors[0] == 7.0) {
				pilot.travel(travel);
				turnCount = 1;
				travel=+5;
			} else {
				travel=10;
				if (lastTurn==false) {
					pilot.rotate(-turnCount);
				} else {
					pilot.rotate(turnCount);
				}
				lastTurn = !lastTurn;
				turnCount++;
			}
		}
		Sound.beep();
	}
}
