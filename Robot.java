/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final XboxController m_driverController = new XboxController(Map.DRIVER_CONTROLLER);
  private final XboxController m_operatorController = new XboxController(Map.OPERATOR_CONTROLLER);

  //Motors
  private final SpeedControllerGroup m_leftMotors =
    new SpeedControllerGroup(new WPI_VictorSPX(Map.LEFT_FRONT_MOTOR), new WPI_VictorSPX(Map.LEFT_REAR_MOTOR));
  private final SpeedControllerGroup m_rightMotors =
    new SpeedControllerGroup(new WPI_VictorSPX(Map.RIGHT_FRONT_MOTOR), new WPI_VictorSPX(Map.RIGHT_REAR_MOTOR));
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  //Encoders
  private final Encoder m_leftEncoder = new Encoder(Map.leftEnc1, Map.leftEnc2);
  private final Encoder m_rightEncoder = new Encoder(Map.rightEnc1, Map.rightEnc2);

  //Auto
  private NetworkTableEntry m_maxSpeed;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    encoders.add("Left Encoder", m_leftEncoder);
    encoders.add("Right Encoder", m_rightEncoder);
    m_leftEncoder.setDistancePerPulse((Math.PI * Map.wheelDiameter) / Map.encoderCPR);
    m_rightEncoder.setDistancePerPulse((Math.PI * Map.wheelDiameter) / Map.encoderCPR);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    //driver controls
    
    double triggerVal = m_driverController.getTriggerAxis(Hand.kRight) - m_driverController.getTriggerAxis(Hand.kLeft);
    double stick = m_driverController.getX(Hand.kLeft) * Map.TURNING_RATE;
    
    m_robotDrive.tankDrive((triggerVal - stick) * Map.DRIVING_SPEED, (triggerVal + stick) * Map.DRIVING_SPEED);

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }


}
