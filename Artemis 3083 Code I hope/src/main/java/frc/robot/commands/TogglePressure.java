// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSystem;

public class TogglePressure extends CommandBase {

  ElevatorSystem elevatorSystem;
    
  public TogglePressure(ElevatorSystem elevatorSystem) {
    
    this.elevatorSystem = elevatorSystem;
    addRequirements(elevatorSystem);
  }
  
  @Override
  public void initialize() {}
  
  @Override
  public void execute() {
    elevatorSystem.toggle();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}