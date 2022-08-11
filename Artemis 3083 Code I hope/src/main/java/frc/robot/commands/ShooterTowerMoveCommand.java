package frc.robot.commands;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.TowerSystem;

public class ShooterTowerMoveCommand extends CommandBase{

    private TowerSystem towerSystem;
    PS4Controller seccontroller;


    public ShooterTowerMoveCommand(TowerSystem towerSystem, PS4Controller seccontroller){
        this.towerSystem = towerSystem;
        this.seccontroller = seccontroller;
        addRequirements(towerSystem);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double turret = seccontroller.getRightX();
        towerSystem.TowerMove(turret * Constants.TOWER_TURRET_STICK_MULTIPLIER);

        double angle = -seccontroller.getLeftY();
        
        if (angle > Constants.TOWER_ANGLE_STICK_THRESHOLD) {
            towerSystem.AngleMove(Constants.TOWER_ANGLE_STICK_SPEED);
        } else if (angle < -Constants.TOWER_ANGLE_STICK_THRESHOLD) {
            towerSystem.AngleMove(-Constants.TOWER_ANGLE_STICK_SPEED);
        } else {
            towerSystem.AngleMove(0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        towerSystem.TowerStop();
        towerSystem.AngleStop();
    }
}