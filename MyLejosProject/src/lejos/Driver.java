import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {
	final static float wheelDiameter = 36; //diameter in mm of wheel
	final static float axleLength = 150; //distance of your two driven wheels
	final static float angularSpeed = 90; //how fast around corners (degrees/sec)
	final static float linearSpeed = 100; //how fast travel straight line
	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, wheelDiameter).offset(-axleLength/2);
		Wheel wRight = WheeledChassis.modelWheel(mRight, wheelDiameter).offset(axleLength/2);
		Chassis chassis = new WheeledChassis(new Wheel[] {wRight, wLeft}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(linearSpeed);
		pilot.setAngularSpeed(angularSpeed);
		Behavior trundle = new Trundle(pilot);
		Behavior escape = new Backup(pilot);
		Behavior stop = new Stop(pilot);
		Behavior speed = new Speed(pilot);
		Arbitrator ab = new Arbitrator(new Behavior[] {trundle, speed, escape, stop});
		Button.ENTER.waitForPressAndRelease();
		ab.go();
	}
}
