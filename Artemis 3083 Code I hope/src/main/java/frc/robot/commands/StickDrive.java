package frc.robot.commands;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSystem;

public class StickDrive extends CommandBase {

    DriveSystem driveSystem;
    PS4Controller controller;

    /** Creates a new NewDriveCommand. */
    public StickDrive(DriveSystem driveSystem, PS4Controller controller) {
        this.driveSystem = driveSystem;
        this.controller = controller;
        addRequirements(driveSystem);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double moveValue = fixAxis(controller.getR2Axis()) - fixAxis(controller.getL2Axis());
        System.out.println(moveValue);
        double rotateValue = controller.getRightX();

        driveSystem.arcadeDrive(moveValue, rotateValue);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveSystem.Stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    private double fixAxis(double value) {
        return (value + 1) / 2.0;
    }
}