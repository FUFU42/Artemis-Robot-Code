// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FeederSystem;

public class FeedCommand extends CommandBase {
  
  private FeederSystem feederSystem;
  
  public FeedCommand(FeederSystem feederSystem) {
    this.feederSystem = feederSystem;
    addRequirements(feederSystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
      feederSystem.StartLiftToShooterMotor();
      feederSystem.Convoyer();
    }

  @Override
  public void end(boolean interrupted) {
    feederSystem.StopFeeder();
    feederSystem.StopLiftMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}