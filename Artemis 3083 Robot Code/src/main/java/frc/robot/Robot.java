package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import frc.robot.commands.CollectorPneumaticLiftCommand;
import frc.robot.commands.CollectorPneumaticLowerCommand;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.AutomaticShootingCommand;
import frc.robot.commands.CollectAndMoveDown;
import frc.robot.commands.CollectorCollectCommand;
import frc.robot.commands.FeedCommand;
import frc.robot.commands.LimeLightTowerAngle;
import frc.robot.commands.MoveElevatorDown;
import frc.robot.commands.MoveElevatorUp;
import frc.robot.commands.NewAutomaticShootingCommand;
import frc.robot.commands.NewDriveCommand;
import frc.robot.commands.ShooterTowerMoveCommand;
import frc.robot.commands.TogglePressure;
import frc.robot.commands.UnLoadCommand;
import frc.robot.subsystems.CollectorSystem;
import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.ElevatorSystem;
import frc.robot.subsystems.FeederSystem;
import frc.robot.subsystems.LimeLightImageProcessing;
import frc.robot.subsystems.ShootSystem;
import frc.robot.subsystems.TowerSystem;

public class Robot extends TimedRobot {

    private DriveSystem driveSystem;
    private CollectorSystem collectorsystem;
    private ShootSystem shootSystem;
    private TowerSystem towerSystem;
    private FeederSystem feederSystem;
    private LimeLightImageProcessing limeLightImageProcessing;
    private ElevatorSystem elevatorSystem;

    private PS4Controller controller;
    private PS4Controller seccontroller;

    private SendableChooser<Command> autoChooser;
    private Command autoCommand = null;
    NetworkTableEntry setpoint;
    NetworkTableEntry feed;
    Compressor compressor;
    
    @Override
    public void robotInit() {
        compressor = new Compressor(PneumaticsModuleType.CTREPCM);
        compressor.enableDigital();

        elevatorSystem = new ElevatorSystem();
        limeLightImageProcessing = new LimeLightImageProcessing();
        driveSystem = new DriveSystem();
        collectorsystem = new CollectorSystem();
        shootSystem = new ShootSystem();
        towerSystem = new TowerSystem();
        feederSystem = new FeederSystem();

        controller = new PS4Controller(0);
        seccontroller = new PS4Controller(2);

        CameraServer.startAutomaticCapture(0);

        /*
         *  OPERATOR CONTROL
         *
         *  CONTROLLER 1: DRIVER
         *      RIGHT STICK Y: DRIVE RIGHT
         *      LEFT STICK Y: DRIVE LEFT
         *
         *  CONTROLLER 2: SYSTEM OPERATOR
         *      TOWER
         *          RIGHT STICK X: TOWER ORIENTATION
         *          RIGHT STICK Y: TOWER ANGLE
         *
         *      COLLECTOR
         *          POV UP [HOLD]: COLLECTOR UP
         *          POV DOWN [HOLD]: COLLECTOR DOWN
         *          L1 [HOLD]: COLLECTOR IN
         *
         * 
         *      SHOOTER
         *          R2 [HOLD] (POWER): SHOOT
         *
         *      FEEDER
         *          ACTIVATED WHEN SHOOTING OR COLLECTING
         *          NOTE: CANNOT COLLECT AND SHOOT AT THE SAME TIME.
         */
        //shootSystem.setDefaultCommand(new ShootWithWheelsCommand(shootSystem, shootSystem.getShooterRpm()));

        driveSystem.setDefaultCommand(new NewDriveCommand(driveSystem, controller));
        towerSystem.setDefaultCommand(new ShooterTowerMoveCommand(towerSystem, seccontroller));
        //shootSystem.setDefaultCommand(new NewAutomaticShootingCommand(limeLightImageProcessing, shootSystem));
        //shootSystem.setDefaultCommand(new ShootWithWheelsCommand (shootSystem, shootSystem.getShooterRpm()));
        
        new POVButton(seccontroller, 180)
                .whileHeld(new CollectorPneumaticLiftCommand(collectorsystem));
        new POVButton(seccontroller, 0)
                .whileHeld(new CollectorPneumaticLowerCommand(collectorsystem));
        new JoystickButton(seccontroller, PS4Controller.Button.kL1.value)
                .whileHeld(new CollectorCollectCommand(collectorsystem, feederSystem));
        new JoystickButton(seccontroller, PS4Controller.Button.kR1.value)
                .whileHeld(new FeedCommand(feederSystem));
        /*new JoystickButton(seccontroller, PS4Controller.Button.kR2.value)
                .whenActive(new ShootWithWheelsCommand(shootSystem, seccontroller, shootSystem.getShooterRpm()));*/
        new JoystickButton(seccontroller, PS4Controller.Button.kTriangle.value)
                .whenActive(new LimeLightTowerAngle(limeLightImageProcessing, towerSystem));
        new JoystickButton(seccontroller, PS4Controller.Button.kSquare.value)
                .whileHeld(new ConveyorCommand(feederSystem));
        new JoystickButton(seccontroller, PS4Controller.Button.kCross.value)
                .whileHeld(new UnLoadCommand(feederSystem, collectorsystem));
        /*new JoystickButton(seccontroller, PS4Controller.Button.kCircle.value)
                .whileHeld(new ShootAtRpm(shootSystem, 920)); //215cm = 8.388 Voltage*/

        new JoystickButton(seccontroller, PS4Controller.Button.kR2.value)
                .whileHeld(new AutomaticShootingCommand(shootSystem, limeLightImageProcessing));


        new JoystickButton(controller, PS4Controller.Button.kCircle.value)
                .whenActive(new TogglePressure(elevatorSystem));
        new POVButton(controller, 270)
                .whileHeld(new MoveElevatorUp(elevatorSystem));
        new POVButton(controller, 90)
                .whileHeld(new MoveElevatorDown(elevatorSystem));

                


        SmartDashboard.putData("Reset Tower Pos", new InstantCommand(()-> towerSystem.resetTowerPosition()));
        SmartDashboard.putData("Reset Driver Pos", new InstantCommand(()-> driveSystem.resetEncoders()));

        autoChooser = new SendableChooser<>();
        autoChooser.addOption("Do Nothing", new InstantCommand());
        autoChooser.addOption("Drive Passed Line", new DriveDistance(driveSystem, 2.5));
        SmartDashboard.putData("Auto", autoChooser);


        setpoint = NetworkTableInstance.getDefault().getTable("pid").getEntry("setpoint");
        setpoint.setDouble(0);
        feed = NetworkTableInstance.getDefault().getTable("pid").getEntry("feed");
        feed.setBoolean(false);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();

        double interrpm = 0.0001*Math.pow(limeLightImageProcessing.getTargetDistance(), 3) - 0.0808 * Math.pow(limeLightImageProcessing.getTargetDistance(), 2) + 21.267 * limeLightImageProcessing.getTargetDistance() + 238.02;

        SmartDashboard.putNumber("Drive L", driveSystem.getDistancePassedLeftM());
        SmartDashboard.putNumber("Drive R", driveSystem.getDistancePassedRightM());

        SmartDashboard.putNumber("Shooter Pos", towerSystem.getTowerPosition());
        SmartDashboard.putNumber("Shooter RPM", shootSystem.getShooterRpm());

        SmartDashboard.putBoolean("Tower Angle At Bottom", towerSystem.IsAngleAtBottom());
        SmartDashboard.putBoolean("Has Ball", feederSystem.HasBall()); //beware
        SmartDashboard.putBoolean("'is zeroed'", towerSystem.IsZeroed());

        SmartDashboard.putNumber("Vision Distance", limeLightImageProcessing.getTargetDistance());
        SmartDashboard.putNumber("Vision Horizontal Offset", limeLightImageProcessing.TxOffset());
        SmartDashboard.putNumber("interpolation rpm", NewAutomaticShootingCommand.rpm);
        SmartDashboard.putNumber("interpolation rpm", interrpm);

        if(towerSystem.IsZeroed()){
            towerSystem.resetTowerPosition();
        }
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void teleopInit() {
        //
    }

    @Override
    public void teleopPeriodic() {
        /*SmartDashboard.putNumber("Comp", compressor.getAnalogVoltage());
        SmartDashboard.putBoolean("Comp2", compressor.getPressureSwitchValue());*/
    }

    @Override
    public void autonomousInit() {
        Command driveDistance = new DriveDistance(driveSystem, 1);
        Command collectorDown = new CollectAndMoveDown(collectorsystem, feederSystem);
        /*autoCommand = new DriveDistance(driveSystem, 2).andThen(new WaitCommand(1), createShootCommand())
                .andThen(driveDistance.raceWith(collectorDown)).andThen(createShootCommand());*/
                //autoCommand = createShootCommand();
                autoCommand = new DriveDistance(driveSystem, 1).andThen(new WaitCommand(1), createShootCommand());
        if (autoCommand != null) {
                autoCommand.schedule();
        }
    }

    private Command createShootCommand() {
        Command shoot = new NewAutomaticShootingCommand (limeLightImageProcessing, shootSystem);
        Command LimeligtAngle = new LimeLightTowerAngle(limeLightImageProcessing, towerSystem);
        Command waitFeed = new WaitCommand(2);
        Command feed = new FeedCommand(feederSystem);
        //Command beforeAuto = shoot.alongWith(LimeligtAngle);
        return LimeligtAngle.andThen(shoot).alongWith(waitFeed.andThen(feed)).withTimeout(4); 
    }
    
    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {
        if (autoCommand != null) {
                autoCommand.cancel();
        }
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
        feed.setBoolean(false);
    }

    @Override
    public void testPeriodic() {
        double rpm = setpoint.getDouble(0);
        shootSystem.ShootAtRpm(rpm);


        if (feed.getBoolean(false)) {
                feederSystem.StartLiftToShooterMotor();
                feederSystem.Convoyer();
        } else {
                feederSystem.StopLiftMotor();
                feederSystem.StopFeeder();
        }
    }
}
