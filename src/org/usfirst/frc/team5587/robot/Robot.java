package org.usfirst.frc.team5587.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase;
import org.usfirst.frc.team5587.robot.RobotDrive;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
	RobotDrive myRobot; // i think we should create a library class for these things. @Daren
	Joystick stick;     // we should probably organise the code a bit before we do some real programming. @Daren
	CameraServer server;
	Encoder eLeftWheels = new Encoder(0,1,false);
	//Encoder eRightWheels = new Encoder(1,2,false);
	//Encoder eLiftPulley = new Encoder(1,2,false);
	
	 /*
	 Encoder liftPulleyCount= new Encoder(aChannel,bChannel, [+or- for direction]);
	 Parameters: aChannel The a channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
                 bChannel The b channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
reverseDirection represents the orientation of the encoder and inverts the output values if necessary 
so forward represents positive values.
	 */
	int autoLoopCounter;
	
    public void robotInit() 
    {
    	myRobot = new RobotDrive(2,3,0,1);
    	stick = new Joystick(0);
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() 
    {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
    	//Hello guys! we are told to write the easier codes, so lets do it soon guys.
    	//also we need to figure out how to use the encoder
    	
    	if(autoLoopCounter < 450) 
		{
    		myRobot.drive(-0.25, 0); 	
			autoLoopCounter++;
		} 
    	else 
		{
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit()
    {
    	server = CameraServer.getInstance();
        server.setQuality(50);
        //the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam0");
        eLeftWheels.reset();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        myRobot.arcadeDrive(stick);
        DriverStation.reportError( "" + eLeftWheels.getRaw(), false);
    } 
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
    	LiveWindow.run();
    	eLeftWheels.updateTable();
    	eLeftWheels.startLiveWindowMode();
    	
        myRobot.arcadeDrive(stick);
    }
    
    /**
     * This will return the speed needed for the second wheel when we turn the robot on an arc.
     */
    public double secondSpeed(double v1, double d, double r)
    {
    	return v1 * d / (d + r);
    }
    
    public double clicks(double d, double r, double c, double deltaTheta)
    {
    	return (d+r) * deltaTheta / c;
    }
    
    public double clicks(double d, double c)
    {
    	return d / c;
    }
}
