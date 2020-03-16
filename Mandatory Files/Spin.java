import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

//this is the testing class in which the robot will turn 180 degrees
public class Spin implements Behavior {
	private MovePilot pilot;
	private SampleProvider sensor, angle;
	private EV3GyroSensor angleSensor;
	private PoseProvider position;
	float[] colors = new float[1];
	float[] angles = new float[1];
	boolean currentRotation;
	Spin(MovePilot p, SampleProvider c, SampleProvider d, EV3GyroSensor s, PoseProvider o) {
		pilot = p;
		sensor = c;
		angle = d;
		angleSensor = s;
		position = o;
		currentRotation = true;
	}
	
	public void action() {
		pilot.stop();
		pilot.travel(-25);
		//
		Pose currentPose = position.getPose();
		turn(-90, currentPose);
		pilot.travel(150);
		currentPose = position.getPose();
		turn(-90, currentPose);
		pilot.travel(100);
	}
	
	public void suppress() {}
		
	public boolean takeControl() {
		sensor.fetchSample(colors, 0);
		return (colors[0] <0.3);
	}
	
	public void correctRotation(Pose currentPose) { //warning, only use when robot is stationary
	 }
	
	public void turn(float targetAngle, Pose currentPose) {
		Delay.msDelay(1000);
		angleSensor.reset();
		//get current pose
		currentPose.setHeading(currentPose.getHeading()-targetAngle);
		//rotate
		pilot.rotate(targetAngle);
		//now to correct the rotation
		Delay.msDelay(1000);
		angle.fetchSample(angles, 0);
		while (-targetAngle != angles[0]) {
			LCD.drawString(Float.toString(angles[0]), 2, 4);
			pilot.rotate(targetAngle-(-angles[0]));
			//finally set the pose
			Delay.msDelay(1000);
			angle.fetchSample(angles, 0);
		}
		position.setPose(currentPose);
	}
}