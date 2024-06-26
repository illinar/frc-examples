// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDMainSubsystem;
import frc.robot.subsystems.LEDPattern;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LEDTopSubsystem;
import frc.robot.subsystems.VisionSubsystem;

import static edu.wpi.first.units.Units.Seconds;

public class RobotContainer {
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final VisionSubsystem vision = new VisionSubsystem();

  private final LEDSubsystem led = new LEDSubsystem(64, 1);
  private final LEDTopSubsystem ledTop = new LEDTopSubsystem(led);
  private final LEDMainSubsystem ledMain = new LEDMainSubsystem(led);

  private final LEDPattern disabled = LEDPattern.solid(Color.kRed).breathe(Seconds.of(2));
  private final LEDPattern enabled = LEDPattern.solid(Color.kGreen).breathe(Seconds.of(2));
  private final LEDPattern defaultPattern = () -> (DriverStation.isDisabled() ? disabled : enabled).applyTo();
  private final LEDPattern blink = LEDPattern.solid(Color.kMagenta).blink(Seconds.of(0.2));
  private final LEDPattern orange = LEDPattern.solid(Color.kOrange);
  
  public RobotContainer() {
    ledTop.setDefaultCommand(ledTop.runPattern(defaultPattern).ignoringDisable(true).withName("LedDefault"));
    ledMain.setDefaultCommand(ledMain.runPattern(defaultPattern).ignoringDisable(true).withName("LedDefault"));

    intake.gamePieceAcquired.onTrue(
      ledMain.runPattern(blink).withTimeout(1.0).withName("LedAcquiredGamePiece"));

    vision.targetAcquired.whileTrue(
      ledTop.runPattern(orange).withName("LedVisionTargetInSight"));

    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
