// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CollectorSystem;
import frc.robot.subsystems.FeederSystem;

public class CollectAndMoveDown extends CommandBase {
  
  CollectorSystem collectorSystem;
  FeederSystem feederSystem;

  public CollectAndMoveDown(CollectorSystem collectorSystem, FeederSystem feederSystem) { //add FeederSystem feederSystem
    this.collectorSystem = collectorSystem;
    this.feederSystem = feederSystem;
    addRequirements(collectorSystem, feederSystem); //add feederSystem
  }
  public double StartTime;

  @Override
  public void initialize() {
    StartTime = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {
    collectorSystem.CollectorFunction();
    if(Timer.getFPGATimestamp()<StartTime+0.9){
      collectorSystem.StartCollectorSolenoid();
    }
    else{
      collectorSystem.StartCollectorSolenoid();
    }
    feederSystem.Convoyer();
  }

  @Override
  public void end(boolean interrupted) {
    collectorSystem.StoptCollectorSolenoid();
    feederSystem.StopFeeder();
  }

  @Override
  public boolean isFinished() {
    /*if(feederSystem.HasBall()){
      return true;
    }
    else{
      return false;
    }*/

    return feederSystem.HasBall();
  }
}