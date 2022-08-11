// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSystem;

public class ShootAtVoltage extends CommandBase {

  private ShootSystem shootSystem;
  private double voltage;

  /** Creates a new ShootAtVoltage. */
  public ShootAtVoltage(ShootSystem shootSystem, double voltage) {
    this.shootSystem = shootSystem;
    this.voltage = voltage;
    addRequirements(shootSystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shootSystem.shootVoltage(voltage);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shootSystem.StopShoot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
