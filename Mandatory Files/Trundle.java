import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

//this is just the basic move forward class which exhibits the lowest priority in the arbitrator
public class Trundle implements Behavior {
	private MovePilot pilot;
	Trundle(MovePilot p) {
		pilot = p;
	}
	
	public void action() {
		if (!pilot.isMoving()) {
			pilot.forward();
		}
	}
	
	public void suppress() {}
		
	public boolean takeControl() {
		return true;
	}
}