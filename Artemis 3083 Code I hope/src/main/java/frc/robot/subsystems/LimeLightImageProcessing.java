// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LimeLightImageProcessing extends SubsystemBase {

    private NetworkTable limelightTable;

    public LimeLightImageProcessing() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

  public double TxOffset(){
    return limelightTable.getEntry("tx").getDouble(0);

  }

  public double TyOffset(){
    return limelightTable.getEntry("ty").getDouble(0);

  }

  public double getTargetDistance(){
    double ty = TyOffset();
    return (Constants.TARGET_HEIGHT - Constants.LIMELIGHT_HEIGHT) / Math.tan(Math.toRadians(Constants.LIMELIGHT_ANGLE + ty));
  }
}
