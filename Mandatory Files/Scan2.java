import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;

//this is the heavily unfinished class where the robot will scan the objects and add them to the pose list
public class Scan implements Behavior {
	private MovePilot pilot;
	private SampleProvider sensor, angleProvider;
	private EV3GyroSensor angleSensor;
	private ArrayList<Pose> poseList;
	private PoseProvider poseProvider;
	
	float[] distances = new float[1];
	float[] angles = new float[1];
	//
	private static String IPaddress = "10.0.1.8";
	private static int port = 1234;
	public static Socket connection = new Socket();
	public static DataInputStream dis;
	public static DataOutputStream dos;
	private static int MAX_READ = 50;
	private static BufferedInputStream in = null;
	private static OutputStream out = null;
	//
	Scan(MovePilot p, SampleProvider s, SampleProvider a, EV3GyroSensor g, List<Pose> l, PoseProvider pp) {
		pilot = p;
		sensor = s;
		angleProvider = a;
		angleSensor = g;
		poseList = (ArrayList<Pose>) l;
		poseProvider = pp;
	}
	
	public void action() {
		pilot.stop();
		Sound.beepSequenceUp();
		//scan qr code and add object, check for duplicate
		
		String[] information = scanQR();
		
		
		//jot pose
		Pose newPose = poseProvider.getPose();
		//alter pose to relative block
		poseList.add(newPose);
		//turn around
		LCD.clear();
		LCD.drawString(newPose.toString(), 0, 2);
	}
	
	//since action returns immediately probably never called
	public void suppress() {}
		//is it my turn?
	public boolean takeControl() {
		sensor.fetchSample(distances, 0);
		return (distances[0] <=0.2);
	}
	
	public String[] scanQR() throws IOException {
		byte[] buffer = new byte[MAX_READ];
		//(new TunePlayer()).start();
		
		LCD.drawString("Waiting  ", 0, 0);
		SocketAddress sa = new InetSocketAddress(IPaddress, port);
		try {
			connection.connect(sa, 1500); // Timeout possible
		} catch (Exception ex) {
			// This connection fail is just ignored - we were probably not trying to connect because there was no
			// Android device
			// Could be Timeout or just a normal IO exception
			LCD.drawString(ex.getMessage(), 0,6);
			connection = null;
		}
		if (connection != null) {
			in = new BufferedInputStream( connection.getInputStream());
			out = connection.getOutputStream();
			LCD.drawString("Connected", 0, 0);
		}
		
		String name = "";
		String xLength= "";
		String yLength= "";
		String zLength= "";
		String orientation= "";
		
		LCD.drawString("Waiting  ", 0, 1);
		while (!Button.ESCAPE.isDown()) {
			if (connection != null) {
				if (in.available() > 0) {
//					LCD.drawString("Chars read: ", 0, 2);
//					LCD.drawInt(in.available(), 12, 2);
					int read = in.read(buffer, 0, MAX_READ);
//					LCD.drawChar('[', 0, 3);
					int newLine = 2;
					int screenLength = 0;
					
					//Variables for the QR argument
					
					int commaNumber = 0;
					
					for (int index = 4 ; index < read ; index++) {
						
						if((index-4)%18==0) {
							newLine++;
							screenLength=0;
						}
						LCD.drawChar((char)buffer[index], screenLength++, newLine);
						
						//------------------------------------------------------
						//Split letters into their sections
						
						if((char)buffer[index] == ',') {
							//Every time a comma is encountered, increment to identify the section.
							commaNumber++;
						}
						if(commaNumber==0) {
							name += (char)buffer[index];
						}
						if(commaNumber==1) {
							xLength += (char)buffer[index];
						}
						if(commaNumber==2) {
							yLength += (char)buffer[index];
						}
						if(commaNumber==3) {
							zLength += (char)buffer[index];
						}
						if(commaNumber==4) {
							orientation += (char)buffer[index];
						}
						//------------------------------------------------------
					}
							
//					LCD.drawChar(']', read + 4, 3);
					out.write("Reply:".getBytes(), 0, 6);
					out.write(buffer, 0, read);
					
					//you can now use the variables; name, xLength, yLength, zLength and orientation as you wish.
					
//					System.out.println(name);
					
				}
			}
		}
		
		String[] objectString = new String[] {name, xLength, yLength, zLength, orientation};
		return objectString;
	}
}