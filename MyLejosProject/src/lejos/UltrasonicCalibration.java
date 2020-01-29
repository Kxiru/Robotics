import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

class UltrasonicCalibration {
    public static void main(String[] args) {
        LCD.drawString("Ready to go?", 2, 2);
        Button.ENTER.waitForPressAndRelease();
        LCD.clear();
        float[] distances = new float[1];
        EV3UltrasonicSensor front = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider distance = front.getDistanceMode();
        while (Button.ESCAPE.isUp()) {
            Button.ENTER.waitForPressAndRelease();
            distance.fetchSample(distances, 0);
            LCD.clear();
            LCD.drawString(Float.toString(distances[0]),2,2);
        }
    }
}