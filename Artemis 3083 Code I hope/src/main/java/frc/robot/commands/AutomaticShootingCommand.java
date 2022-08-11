// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightImageProcessing;
import frc.robot.subsystems.ShootSystem;

public class AutomaticShootingCommand extends CommandBase {
  ShootSystem shootSystem;
  LimeLightImageProcessing limeLightImageProcessing;
  
  public AutomaticShootingCommand(ShootSystem shootSystem, LimeLightImageProcessing limeLightImageProcessing){
    this.shootSystem = shootSystem;
    this.limeLightImageProcessing = limeLightImageProcessing;
    addRequirements(shootSystem, limeLightImageProcessing);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double distance = limeLightImageProcessing.getTargetDistance();
    double rpm = shootSystem.interpolation.value(distance);
    SmartDashboard.putNumber("interpolation rpm", rpm);
    SmartDashboard.putNumber("function rpm", 0.0001*Math.pow(distance, 3) - 0.0808 * Math.pow(distance, 2) + 21.267 * distance + 488.02);
    shootSystem.ShootAtRpm(rpm);
    System.out.println(rpm);
    //shootSystem.ShootAtRpm(rpm);
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
