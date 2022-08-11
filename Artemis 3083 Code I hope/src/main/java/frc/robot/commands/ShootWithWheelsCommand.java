// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSystem;

public class ShootWithWheelsCommand extends CommandBase {

    private ShootSystem shootSystem;
    double rpm;
    PS4Controller secondController;
    double lockedSpeed;
    boolean isLocked;
    public ShootWithWheelsCommand(ShootSystem shootSystem, PS4Controller secondController, double rpm){

        this.shootSystem = shootSystem;
        this.rpm = rpm;
        this.secondController = secondController;
        addRequirements(shootSystem);
    }

    @Override
    public void initialize() {
        isLocked = false;
    }

    @Override
    public void execute() {
        if (isLocked) {
            shootSystem.Shoot(lockedSpeed);

            if (secondController.getOptionsButton()) {
                lockedSpeed += 0.001;
            } else if (secondController.getShareButton()) {
                lockedSpeed -= 0.001;
            }

            if (secondController.getR3Button()) {
                isLocked = false;
            }

        } else {
            double axis = secondController.getRawAxis(4);
            axis += 1;
            axis /= 2.0;
            shootSystem.Shoot(axis);

            if (secondController.getR3Button()) {
                isLocked = true;
                lockedSpeed = axis;
            }
        }
    }
    
    @Override
    public boolean isFinished() {
        return !isLocked && !secondController.getR2Button();
    }

    @Override
    public void end(boolean interrupted) {
        shootSystem.StopShoot();
    }
}