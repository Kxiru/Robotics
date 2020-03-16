import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Control {
	final static float wheelDiameter = 55.8f;
	final static float axleLength = 115.5f;
	public static void main(String[] args) {		//
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		//
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, wheelDiameter).offset(-axleLength/2);
		Wheel wRight = WheeledChassis.modelWheel(mRight, wheelDiameter).offset(axleLength/2);
		Chassis chassis = new WheeledChassis(new Wheel[] {wRight, wLeft}, WheeledChassis.TYPE_DIFFERENTIAL);
		//
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(100);
		pilot.setAngularSpeed(25);
		//
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider distanceProvider = distanceSensor.getDistanceMode();
		//
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
		SampleProvider colorProvider = colorSensor.getRedMode();
		//
		EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S4);
		SampleProvider angleProvider = gyroSensor.getAngleMode();
		//
		List<Pose> poses = new ArrayList<Pose>();
		PoseProvider position = new OdometryPoseProvider(pilot); 
		//
		Behavior trundle = new Trundle(pilot);
		Behavior scan = new Scan(pilot, distanceProvider, angleProvider, gyroSensor, poses, position);
		Behavior spin = new TestSpin(pilot, colorProvider, angleProvider, gyroSensor, position);
		Arbitrator ab = new Arbitrator(new Behavior[] {trundle, spin, scan});
		Button.ENTER.waitForPressAndRelease();
		ab.go();
	}
}
