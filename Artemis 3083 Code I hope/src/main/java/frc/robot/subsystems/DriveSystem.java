// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSystem extends SubsystemBase {

  WPI_TalonFX talonLF;
  WPI_TalonFX talonLR;
  WPI_TalonFX talonRR; 
  WPI_TalonFX talonRF;
  double joyR;
  double joyL;
  
  public DriveSystem() {
    talonLF = new WPI_TalonFX(2);
    talonLR = new WPI_TalonFX(1);
    talonRR = new WPI_TalonFX(3); 
    talonRF = new WPI_TalonFX(4);

    talonLF.setInverted(false);
    talonLR.setInverted(false);
    talonRF.setInverted(false);
    talonRR.setInverted(false);

    resetEncoders();
  }

  public void arcadeDrive(double moveValue, double rotateValue){
    double rSpeed, lSpeed;

    if (moveValue > 0.0) {
        if (rotateValue > 0.0) {
            lSpeed = moveValue - rotateValue;
            rSpeed = Math.max(moveValue, rotateValue);
        } else {
            lSpeed = Math.max(moveValue, -rotateValue);
            rSpeed = moveValue + rotateValue;
        }
    } else {
        if (rotateValue > 0.0) {
            lSpeed = -Math.max(-moveValue, rotateValue);
            rSpeed = moveValue + rotateValue;
        } else {
            lSpeed = moveValue - rotateValue;
            rSpeed = -Math.max(-moveValue, -rotateValue);
        }
    }

    drive_func(lSpeed, rSpeed);
}

  public double getDistancePassedLeftM() {
    return -talonLF.getSelectedSensorPosition() / Constants.TALON_FX_PPR / Constants.DRIVE_GEAR_RATIO * Constants.DRIVE_WHEEL_CIRCUMEFERENCE_M;
  }

  public double getDistancePassedRightM() {
    return talonRF.getSelectedSensorPosition() / Constants.TALON_FX_PPR / Constants.DRIVE_GEAR_RATIO * Constants.DRIVE_WHEEL_CIRCUMEFERENCE_M;
  }

  public double getDistancePassedM() {
    return (getDistancePassedLeftM() + getDistancePassedRightM()) / 2.0;
  }

  public void resetEncoders() {
    talonLF.setSelectedSensorPosition(0);
    talonRF.setSelectedSensorPosition(0);
  }
  
  
  public void drive_func(double lSpeed, double rSpeed){
    if (lSpeed > Constants.MIN_SPEED || lSpeed < -Constants.MIN_SPEED || rSpeed < -Constants.MIN_SPEED || rSpeed > Constants.MIN_SPEED)
      {
        talonLR.set(-lSpeed * 1.5);
        talonLF.set(-lSpeed * 1.5);
        talonRR.set(rSpeed * 1.5);
        talonRF.set(rSpeed * 1.5);
      }
      else
      {
        talonLR.set(0);
        talonLF.set(0);
        talonRR.set(0);
        talonRF.set(0);
      }
    
  }
  
  public void Stop() {
      talonLR.set(0);
      talonLF.set(0);
      talonRR.set(0);
      talonRF.set(0);
    }

}
