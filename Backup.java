import java.util.Random;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Backup implements Behavior {
	private MovePilot turner;
	private EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
	private SampleProvider sp = us.getDistanceMode();
	private Random rgen = new Random();
	private float[] samples = new float[1];
	
	Backup(MovePilot p) {
		turner = p;
	}
	public void action() {
		turner.travel(-50);
		turner.rotate((2 * rgen.nextInt(2) - 1) * 30);
	}
	//it is not sensible to suppress this behavior. just let it finish
	public void suppress() {};
	
	public boolean takeControl() {
		sp.fetchSample(samples,  0);
		return (samples[0] < 0.20f);
	}
}
