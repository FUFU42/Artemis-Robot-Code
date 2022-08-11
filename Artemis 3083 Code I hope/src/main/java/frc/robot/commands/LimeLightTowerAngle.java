// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightImageProcessing;
import frc.robot.subsystems.TowerSystem;

public class LimeLightTowerAngle extends CommandBase {
  LimeLightImageProcessing lightImageProcessing;
  TowerSystem towerSystem;
  /* Creates a new LimeLightAngle. */
  public LimeLightTowerAngle(LimeLightImageProcessing lightImageProcessing, TowerSystem towerSystem) {
    this.lightImageProcessing = lightImageProcessing;
    this.towerSystem = towerSystem;

    addRequirements(towerSystem, lightImageProcessing);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double angle = lightImageProcessing.TxOffset();

    if (angle != 0)
    {
      double speed = MathUtil.clamp(Math.abs(angle) / 70.0, 0.05, 0.5) * Math.signum(angle);
      towerSystem.TowerMove(speed);
    } else {
      towerSystem.TowerStop();
    }

    SmartDashboard.putNumber("Angle", angle);

  }

  @Override
  public void end(boolean interrupted) {
    towerSystem.TowerStop();
  }

  @Override
  public boolean isFinished() {
    double angle = lightImageProcessing.TxOffset();
    return angle >= -1 && angle <= 1;
  }
}
