import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;

//this is the heavily unfinished class where the robot will scan the objects and add them to the pose list
public class Scan implements Behavior {
	private MovePilot pilot;
	private SampleProvider sensor, angleProvider;
	private EV3GyroSensor angleSensor;
	private ArrayList<Pose> poseList;
	private PoseProvider poseProvider;
	
	float[] distances = new float[1];
	float[] angles = new float[1];
	
	Scan(MovePilot p, SampleProvider s, SampleProvider a, EV3GyroSensor g, List<Pose> l, PoseProvider pp) {
		pilot = p;
		sensor = s;
		angleProvider = a;
		angleSensor = g;
		poseList = (ArrayList<Pose>) l;
		poseProvider = pp;
	}
	
	public void action() {
		pilot.stop();
		Sound.beepSequenceUp();
		//scan qr code and add object, check for duplicate
		//jot pose
		Pose newPose = poseProvider.getPose();
		//alter pose to relative block
		poseList.add(newPose);
		//turn around
		LCD.clear();
		LCD.drawString(newPose.toString(), 0, 2);
	}
	
	//since action returns immediately probably never called
	public void suppress() {}
		//is it my turn?
	public boolean takeControl() {
		sensor.fetchSample(distances, 0);
		return (distances[0] <=0.2);
	}
}
