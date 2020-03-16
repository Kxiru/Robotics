import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class TestPose {
	final static float wheelDiameter = 55.8f; //diameter in mm of wheel
	final static float axleLength = 112.5f; //distance of your two driven wheels
	final static float angularSpeed = 50; //how fast around corners (degrees/sec)
	final static float linearSpeed = 50; //how fast travel straight line
	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, wheelDiameter).offset(-axleLength/2);
		Wheel wRight = WheeledChassis.modelWheel(mRight, wheelDiameter).offset(axleLength/2);
		Chassis chassis = new WheeledChassis(new Wheel[] {wRight, wLeft}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(linearSpeed);
		pilot.setAngularSpeed(angularSpeed);
		PoseProvider position = new OdometryPoseProvider(pilot);
		LCD.drawString(position.getPose().toString(), 2, 2);
		Button.ENTER.waitForPressAndRelease();
		pilot.travel(100);
		Delay.msDelay(1000);
		LCD.clear();
		LCD.drawString(position.getPose().toString(), 2, 2);
		Button.ENTER.waitForPressAndRelease();
		pilot.rotate(90);
		Delay.msDelay(1000);
		LCD.clear();
		LCD.drawString(position.getPose().toString(), 2, 2);
		Button.ENTER.waitForPressAndRelease();
		pilot.travel(100);
		Delay.msDelay(1000);
		LCD.clear();
		LCD.drawString(position.getPose().toString(), 2, 2);
		Button.ENTER.waitForPressAndRelease();
	}
}