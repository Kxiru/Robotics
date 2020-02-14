import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Speed implements Behavior {
	private MovePilot p;
	EV3ColorSensor ls = new EV3ColorSensor ( SensorPort . S1 );
	SampleProvider light = ls.getRedMode();
	private float[] ambiance = new float[1];
	
	Speed(MovePilot p) {
		this.p = p;
	}
	public void action() {
		if (p.getLinearSpeed()!=500) {
			p.setLinearSpeed(500);
			p.forward();
		}
	}
	//it is not sensible to suppress this behavior. just let it finish
	public void suppress() {};
	
	public boolean takeControl() {
		light.fetchSample(ambiance, 0);
		return (ambiance[0]>=0.25f);
	}
}
