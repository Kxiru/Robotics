import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class HelloWorld {

	public static void main(String[] args) {
		Button.ENTER.waitForPressAndRelease();
		Button.LEDPattern (4);
		Sound.beepSequenceUp ();
		LCD.clear ();
		LCD.drawString ("HELLO", 2, 2);
		Delay.msDelay (1000);
		LCD.drawString ("WORLD", 2, 4 );
		Button.ENTER.waitForPressAndRelease();
	}
}
