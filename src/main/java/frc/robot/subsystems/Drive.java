/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.PowerDrive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * Add your docs here.
 */
public class Drive extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  final public WPI_TalonSRX
    rightMaster = new WPI_TalonSRX(RobotMap.rightMaster),
    rightSlave1 = new WPI_TalonSRX(RobotMap.rightSlave1),
    rightSlave2 = new WPI_TalonSRX(RobotMap.rightSlave2),
    leftMaster = new WPI_TalonSRX(RobotMap.leftMaster),
    leftSlave1 = new WPI_TalonSRX(RobotMap.leftSlave1),
    leftSlave2 = new WPI_TalonSRX(RobotMap.leftSlave2);
  
  public Drive() {
    super();
    // constructs and configures all six drive motors
    // restore everything to known factory default state
    rightMaster.configFactoryDefault();
    rightSlave1.configFactoryDefault();
    rightSlave2.configFactoryDefault();
    leftMaster.configFactoryDefault();
    leftSlave1.configFactoryDefault();
    leftSlave2.configFactoryDefault();
    // now configure them
    rightSlave1.follow(rightMaster);
    rightSlave2.follow(rightMaster);
    leftSlave1.follow(leftMaster);
    leftSlave2.follow(leftMaster);
    rightSlave1.setInverted(InvertType.FollowMaster);
    rightSlave2.setInverted(InvertType.FollowMaster);
    leftSlave1.setInverted(InvertType.FollowMaster);
    leftSlave2.setInverted(InvertType.FollowMaster);
    setNeutralMode(NeutralMode.Brake);
    rightMaster.setInverted(InvertType.InvertMotorOutput);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new PowerDrive());
  }
  
  public void setNeutralMode(NeutralMode mode) {
    //method to easily set the neutral mode of all of the driveTrain motors
    rightMaster.setNeutralMode(mode);
    rightSlave1.setNeutralMode(mode);
    rightSlave2.setNeutralMode(mode);
    leftMaster.setNeutralMode(mode);
    leftSlave1.setNeutralMode(mode);
    leftSlave2.setNeutralMode(mode);
  }

  // 2 commands to simply set the power of the left and right side of the robot
  public void setPower(double rightPower, double leftPower) {
    rightMaster.set(rightPower);
    leftMaster.set(leftPower);
  }

  public void setPower(double power) {
    rightMaster.set(power);
    leftMaster.set(power);
  }

  // Drive the robot using a joystick
  public void setPowerArcade(double forward, double turn) {
    double max = Math.abs(forward) + Math.abs(turn);
    double scale = (max <= 1.0) ? 1.0 : (1.0 / max);
    rightMaster.set(scale * (forward + turn));
    leftMaster.set(scale * (forward - turn));
  }
}
