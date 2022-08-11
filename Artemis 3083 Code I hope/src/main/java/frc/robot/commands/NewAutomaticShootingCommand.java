// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightImageProcessing;
import frc.robot.subsystems.ShootSystem;

public class NewAutomaticShootingCommand extends CommandBase {
  public static double rpm;
LimeLightImageProcessing limeLightImageProcessing;
  ShootSystem shootSystem;
  /** Creates a new Command. */
  public NewAutomaticShootingCommand(LimeLightImageProcessing limeLightImageProcessing, ShootSystem shootSystem) {
    this.limeLightImageProcessing = limeLightImageProcessing;
    this.shootSystem = shootSystem;
    addRequirements(limeLightImageProcessing, shootSystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double distance = limeLightImageProcessing.getTargetDistance();
    double rpm = 0.0001*Math.pow(distance, 3) - 0.0808 * Math.pow(distance, 2) + 21.267 * distance + 488.02;
    shootSystem.ShootAtRpm(rpm);
    System.out.println(rpm);
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
