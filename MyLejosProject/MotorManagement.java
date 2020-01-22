import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class MotorManagement {
	public static void main(String[] args) {
		LCD.drawString("Ready to go?", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		BaseRegulatedMotor targetMotor = new EV3LargeRegulatedMotor ( MotorPort . A );
		targetMotor.setSpeed(100);
		targetMotor.forward();
		while (Button.ENTER.isUp()) {
			if (targetMotor.isStalled()) {
				Sound.beep();
			}
		}
	}
}
