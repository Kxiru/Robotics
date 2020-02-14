import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Stop implements Behavior {
	private MovePilot p;
	NXTSoundSensor ss = new NXTSoundSensor ( SensorPort . S3 );
	SampleProvider sound = ss.getDBAMode ();
	private float[] noise = new float[1];
	
	Stop(MovePilot p) {
		this.p = p;
	}
	public void action() {
		p.stop();
		System.exit(0);
	}
	//it is not sensible to suppress this behavior. just let it finish
	public void suppress() {};
	
	public boolean takeControl() {
		sound.fetchSample(noise, 0);
		return (noise[0] >= 0.75f);
	}
}
