import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;

public class PilotSquare {
	final static float wheelDiameter = 36; //diameter in mm of wheel
	final static float axleLength = 150; //distance of your two driven wheels
	final static float angularSpeed = 90; //how fast around corners (degrees/sec)
	final static float linearSpeed = 100; //how fast travel straight line
	public static void main(String[] args) throws DestinationUnreachableException {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, wheelDiameter).offset(-axleLength/2);
		Wheel wRight = WheeledChassis.modelWheel(mRight, wheelDiameter).offset(axleLength/2);
		Chassis chassis = new WheeledChassis(new Wheel[] {wRight, wLeft}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(linearSpeed);
		pilot.setAngularSpeed(angularSpeed);
		LCD.drawString("Ready to go?", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		/*for (int i=0; i<4; i++) {
			pilot.travel(100);
			pilot.rotate(90);
		}*/
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
		/*Navigator navigator = new Navigator(pilot, poseProvider);
		Path path = new Path();     
		path.add(new Waypoint(200, 0));
		path.add(new Waypoint(200, 200));
		path.add(new Waypoint(0, 200));
		path.add(new Waypoint(0, 0));  
		LCD.drawString(String.valueOf(poseProvider.getPose()), 2, 2);
		Delay.msDelay(2000);
		navigator.followPath(path);
		navigator.waitForStop();
		LCD.clear();
		LCD.drawString(String.valueOf(poseProvider.getPose()), 2, 2);
		Button.ENTER.waitForPressAndRelease();*/
		Rectangle boundary = new Rectangle(0,0,120,90);
		Line[] lines = new Line[] {new Line(25,30,25,80),new Line(50,40,70,40)};
		Waypoint destination = new Waypoint(70,80);
		LineMap obstacles = new LineMap(lines, boundary);
		ShortestPathFinder finder = new ShortestPathFinder(obstacles);
		Navigator navigator = new Navigator(pilot, poseProvider);
		try {
			Path newpath = finder.findRoute(new Pose(20,15,90), destination);
			navigator.followPath(newpath);
			navigator.waitForStop();
		} catch (DestinationUnreachableException e) {
			LCD.drawString("Destination unreachable",2,2);
		}
		Button.ENTER.waitForPressAndRelease();
	}
}
