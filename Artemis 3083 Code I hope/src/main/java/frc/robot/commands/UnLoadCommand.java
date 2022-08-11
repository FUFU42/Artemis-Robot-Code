// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CollectorSystem;
import frc.robot.subsystems.FeederSystem;

public class UnLoadCommand extends CommandBase {
  FeederSystem feederSystem;
  CollectorSystem collectorSystem;

  public UnLoadCommand(FeederSystem feederSystem, CollectorSystem collectorSystem) {
    this.feederSystem = feederSystem;
    this.collectorSystem = collectorSystem;
    addRequirements(feederSystem, collectorSystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    feederSystem.ConvoyBack();
    feederSystem.LiftMotorDownFromShooter();
    collectorSystem.BallPullOut();
  }

  @Override
  public void end(boolean interrupted) {
    feederSystem.StopFeeder();
    feederSystem.StopLiftMotor();
    collectorSystem.StopCollect();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
