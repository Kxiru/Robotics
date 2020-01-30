import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class FollowLine {
	
	static BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor ( MotorPort . A );
	static BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
	static final float safe = 0.5f; 
	public static void main(String[] args) {
		Button.ENTER.waitForPressAndRelease();
		mLeft.setSpeed(100);
		mRight.setSpeed(100);
		EV3ColorSensor follow = new EV3ColorSensor(SensorPort.S1);
		SampleProvider sp = follow.getRedMode();
		float[] samples = new float[1];
		boolean lastTurn = false;
		int turnCount = 1;
		while(Button.ENTER.isUp()) {
			sp.fetchSample(samples, 0);
			if (samples[0] < safe) {
				synchroniseMotor(mLeft,mRight);
				turnCount = 1;
			} else {
				for (int i=0; i<=turnCount; i++) {
					if (lastTurn==false) {
						turnLeft(mLeft,mRight);
					} else {
						turnRight(mLeft,mRight);
					}
					Delay.msDelay(100);
				}
				lastTurn = !lastTurn;
				turnCount++;
			}
		}
	}
	
	public static void synchroniseMotor(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
	}
	public static void turnLeft(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.backward();
		mRight.forward();
		mLeft.endSynchronization();
	}
	public static void turnRight(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.backward();
		mLeft.endSynchronization();
	}
}

// black line -->0.0
// white line -->0.74

/*
sp.fetchSample(samples, 0);
if (samples[0] > safe) {
	mLeft.forward();
	sp.fetchSample(samples, 0);
	if (samples[0] < safe) {
		mRight.forward();
	}
	else {
		mLeft.forward();
	}
}
else {
	float updateSafe = samples[0];
	mRight.forward();
	sp.fetchSample(samples, 0);
	if (samples[0] < updateSafe) {
		mLeft.forward();
	}
	else {
		mRight.forward();
    }
}*/