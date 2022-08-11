// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSystem extends SubsystemBase {
  
  WPI_TalonFX elevateR;
  WPI_TalonFX elevateL;

  Solenoid solenoidL;
  
  public ElevatorSystem() {
    elevateL = new  WPI_TalonFX(16);
    elevateR = new  WPI_TalonFX(15);

    elevateL.setInverted(true);

    solenoidL = new Solenoid(PneumaticsModuleType.CTREPCM, 7);
  }
    
  public void MoveUp(){
    elevateL.set(1);
    elevateR.set(1);
  }

  public void MoveDown(){
    elevateL.set(-1);
    elevateR.set(-1);
  }

  public void Stop(){
    elevateL.set(0);
    elevateR.set(0);;
  }

  public void toggle(){
    solenoidL.toggle();
  }   
}