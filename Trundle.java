import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
	private MovePilot pilot;
	Trundle(MovePilot p) {
		pilot = p;
	}
	//start trundling and return control immediately
	public void action() {
		pilot.forward();
	}
	//since action returns immediately probably never called
	public void suppress() {}
		//is it my turn?
	public boolean takeControl() {
		return true;
	}
}
