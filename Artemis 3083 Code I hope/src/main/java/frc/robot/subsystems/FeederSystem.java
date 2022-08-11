// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSystem extends SubsystemBase {
 
  CANSparkMax motor;
  CANSparkMax liftmotor;
  ColorSensorV3 hasBall;

  public FeederSystem() {
    motor = new CANSparkMax(14, MotorType.kBrushless);
    liftmotor = new CANSparkMax(12, MotorType.kBrushless);

    motor.setInverted(true);
    liftmotor.setInverted(true);

    hasBall = new ColorSensorV3(I2C.Port.kOnboard);
    //motor.getEncoder();
  }

  public void Convoyer(){
    motor.set(1);
  }

  public void StopFeeder(){
    motor.set(0);
  }


  public boolean HasBall(){
    return hasBall.getProximity() >= 90;
  }

  public void StartLiftToShooterMotor(){
    liftmotor.set(0.9);
  } 

  public void StopLiftMotor(){
    liftmotor.stopMotor();
  }

  public void ConvoyBack(){
    motor.set(-1);
  }

  public void LiftMotorDownFromShooter(){
    liftmotor.set(-1);
  }

  public double getMotorTemperature(){
    return motor.getMotorTemperature();
  }
}
