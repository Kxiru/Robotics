import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class GyroCalibration {
    public static void main(String[] args) {
        LCD.drawString("Ready to go?", 2, 2);
        Button.ENTER.waitForPressAndRelease();
        LCD.clear();
        EV3GyroSensor down = new EV3GyroSensor(SensorPort.S4);
		SampleProvider color = down.getAngleMode();
		float[] distances = new float[1];
        while (Button.ESCAPE.isUp()) {
            Button.ENTER.waitForPressAndRelease();
            color.fetchSample(distances, 0);
            LCD.clear();
            LCD.drawString(Float.toString(distances[0]),2,2);
        }
        down.close();
    }
}