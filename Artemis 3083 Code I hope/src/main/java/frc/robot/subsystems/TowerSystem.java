package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TowerSystem extends SubsystemBase {

    CANSparkMax angleMotor;
    CANSparkMax towerMotor;
    RelativeEncoder towerEncoder;
    DigitalInput angleLimiterSwitch;
    DigitalInput lightgate;

    public TowerSystem() {
        angleMotor = new CANSparkMax(11, MotorType.kBrushless);
        towerMotor = new CANSparkMax(7, MotorType.kBrushless);
        towerEncoder = towerMotor.getEncoder();
        angleLimiterSwitch = new DigitalInput(0);
        lightgate = new DigitalInput(2);

        resetTowerPosition();
    }

    public boolean IsAngleAtBottom() {
        return !angleLimiterSwitch.get();
    }

    public void AngleMove(double speed){
        if (!IsAngleAtBottom() || speed > 0) {
            angleMotor.set(speed);
        } else {
            angleMotor.set(0);
        }
    }

    public void AngleStop(){
        angleMotor.set(0);
    }

    public double getTowerPosition() {
        double angle = towerEncoder.getPosition() * Constants.TOWER_GEAR_RATIO * 360;
        angle %= 360;
        if (angle < 0) angle += 360;

        return angle;
    }

    public void resetTowerPosition() {
        towerEncoder.setPosition(0);
    }

    public void TowerMove(double speed){
        if (speed < 0 && (getTowerPosition() <= 180 || getTowerPosition() >= 290)) {
            towerMotor.set(speed);
        } else if (speed > 0 && (getTowerPosition() <= 70 || getTowerPosition() >= 180)) {
            towerMotor.set(speed);
        } else {
            towerMotor.set(0);
        }
    }

    public void TowerStop(){
        towerMotor.set(0);
    }

    public boolean IsZeroed(){
        return lightgate.get();
    }
}