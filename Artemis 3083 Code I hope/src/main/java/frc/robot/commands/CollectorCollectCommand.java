// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CollectorSystem;
import frc.robot.subsystems.FeederSystem;

public class CollectorCollectCommand extends CommandBase {

  CollectorSystem collectorSystem;
  FeederSystem feederSystem;

  public CollectorCollectCommand(CollectorSystem collectorsystem, FeederSystem feederSystem) { // a command that was made to move the מגלול
    this.collectorSystem = collectorsystem;
    this.feederSystem = feederSystem;

    addRequirements(collectorSystem, feederSystem);
}

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
      collectorSystem.CollectorFunction();
      feederSystem.Convoyer();
  }

  @Override
  public void end(boolean interrupted) {
    collectorSystem.StopCollect();
    feederSystem.StopFeeder();
  }


  @Override
  public boolean isFinished() {
    return feederSystem.HasBall();
  }
}
