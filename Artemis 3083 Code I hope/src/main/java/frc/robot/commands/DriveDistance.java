// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSystem;

public class DriveDistance extends CommandBase {

  private DriveSystem driveSystem;
  private double distanceToDrive;

  public DriveDistance(DriveSystem driveSystem, double distanceToDrive) {
    this.driveSystem = driveSystem;
    this.distanceToDrive = distanceToDrive;
    addRequirements(driveSystem);
  }

  @Override
  public void initialize() {
    driveSystem.resetEncoders();
  }

  @Override
  public void execute() {
    double speed = 0.3*((distanceToDrive - driveSystem.getDistancePassedLeftM()) / distanceToDrive); //0.7 - (driveSystem.getDistancePassedM() / distanceToDrive);
    if (speed < 0.1) {
      speed = 0.1;
    }
    driveSystem.drive_func(speed, speed);
    SmartDashboard.putNumber("Aoutonomous speed:", speed);
  } 

  @Override
  public void end(boolean interrupted) {
    driveSystem.Stop();
  }

  
  @Override
  public boolean isFinished() {
    return driveSystem.getDistancePassedM() >= distanceToDrive;
  }
}
