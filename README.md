# TCSS305-Group3-Project

TCSS 305 Tetris

Autumn 2023

Tetris Project, Group 3. This project is a class group project that enhances knowledge of Git and working with GUI and JSwing.

## Sprint 3 Contribution

### Group Members:

- **Bhavneet Bhargava:**
  - Worked on the scoring algorithm implementation.
  - Fixed sound volume down feature.
  - Continued working on extra credit features
  - Checkstyle commits to clean up code

- **Melissa D Harvey:**
  - Worked on sound/music addition and additional extra credit pieces.
  - Implemented a leaderboard system.
  - Created a title screen with self-created sprite characters.
  - Checkstyle commits to clean up code 

- **Quienten Miller:**
  - Worked on the difficulty level, including speeding up the timer.
  - Troubleshooted and fixed a bug with the s, z, l, and j pieces in the next piece panel.
  - Added a ghost block feature
  - Checkstyle commits to clean up code

- **Kaely Willhite:**
  - Added additional piece transformation controls and updated the control panel.
  - Updated New Game to reset the score and be inaccessible during a running game.
  - Worked on flipping tetris pieces and design implementations.
  - Checkstyle commits to clean up code

- **Arthur Fornia:**
  - Checkstyle commits to clean up code.
  - Lines cleared display

## Sprint 3 Meetings

### [Meeting Document] 
##  https://docs.google.com/document/d/1nmbdtyDMg8Dnn0xuQwyl4gFJ1OT_Dtqet0WM5Z6dk3E/edit?usp=sharing

### Meeting Logs:

- **Sunday 12/3/23: 11:00 am - 12:00 pm**
  - Attending: Quienten, Mel, Bhav, Kaely, and Arthur
  - Where: Discord
  - Worked On/Discussed: Set up Schedule, created checklist, discussed plan of attack, began work on lines cleared display.
  - To do in between meetings: N/A
  - Plan for Next Meeting: Continue work on the level system.

- **Sunday 12/3/23: 7:00 pm - 8:00 pm**
  - Attending: Quienten, Mel, Bhav, Kaely, and Arthur
  - Where: Discord
  - Worked On/Discussed: Finished work on lines cleared display, worked on the difficulty level, reworking lines cleared.
  - To do in between meetings: N/A
  - Plan for Next Meeting: Continue working on the score system.

- **Monday 12/4/23 8:00 pm - 9:00 pm**
  - Attending: Mel, Kaely, Quienten, and Bhav
  - Where: Discord
  - Worked On/Discussed: Added additional piece transformation controls, worked on sound/music, identified a bug with next piece panel, added scoring system, worked on ghost block feature.
  - To do in between meetings: N/A
  - Plan for Next Meeting: Focus on all remaining requirements, clarify backwards piece bug in Office Hours.
  - Absent: Arthur

- **Tuesday 12/5/23 12 pm - 3:30 pm**
  - Attending: Quienten, Kaely, Bhav, Mel, and Arthur
  - Where: Campus
  - Worked On/Discussed: Cleaned up code, added menu item for scoring details, updated New Game, updated Javadoc, added sound to the game.
  - To do in between meetings: Office hours questions.
  - Plan for Next Meeting: Continue working on remaining requirements.

- **Wednesday 12/6/23 8 pm - 9:30 pm**
  - Attending: Quienten, Kaely, Bhav, Mel
  - Where: Discord
  - Worked On/Discussed: Design implementations, flipped tetris pieces, finalizing extra credit additions.
  - To do in between meetings: Finalize extra credit additions.
  - Plan for Next Meeting: Merge all, finalize project, write up executive summary.
  - Absent: Arthur

- **Saturday 12/9/23 11:00 am -**
  - Attending: Quienten, Kaely, Bhav, Mel (joining at 11 am), Arthur (joined at 1:10 pm)
  - Where: Discord
  - Worked On/Discussed: Updated graphics in panels, added assets for UI, worked on leaderboard system, created gradients for backgrounds, implemented title screen, added escape key to end the game early, worked on minor adjustments and checkstyle clearing.
  - To do in between meetings: Finish readme.
  - Plan for Next Meeting: Finalize project, documentation, and submit.

## Sprint 3 Comments

## Extra Features Implemented

# Model Changes
    -  Ghostpiece: a private helper method updateGhostPiece() was added that creates a MovableTetrisPiece at the same position as myCurrentPiece and drops it until it collides with a frozen block. 
       This private helper method was put within move(), newGame(), and down() (when a piece lock occurs) to ensure the ghost piece was always respondent to myCurrentPiece
    -  updateGhostPiece() at lines 349-359
    -  Called at 201, 256, and 415

# Extra Credit:
    - Leaderboard: Leaderboard was implemented using a DefaultTableModel and JTable. Leaderboard reads a 
      file (to be displayed on the JTable) that is updated each time a new person and score is added to the JTable.
      This file contains person's name and score as objects.
    - Sound: Sounds were taken from this website: 
             https://downloads.khinsider.com/game-soundtracks/album/tetris-gb
    - Sounds/Music: AudioPlayer which plays the audio for the game, uses Clip to play the audios.
      There are 4 audios, clear line (played when the user clears a line),  level up (plays when the user levels up), 
      game over (played when game is over) and background music (plays when game is in current motion).
    - Themes: A theme system was put in place to draw the game pieces differently at will, a Theme interface defines a theme with a single generic type, this type is later to be used to determine what a TetrisPiece maps to.
      ColorTheme is a Theme<Color> that contains a map of TetrisPieces to java.awt.Colors.
      ImageTheme is a Theme<BufferedImage> that contains a map of TetrisPieces to BufferedImages.
      TetrisThemeController is class that contains the current Theme<?> and uses the same Singleton design pattern implemented in the model class. This system used to control the game theme used by the menu bar at the top of the window and onGameOver() method in Application.
      On the receiving end a Theme<?> is stored in BoardPanel that is later casted to a ColorTheme or ImageTheme. If it's a ColorTheme, the paintComponent will use rectangles with a black stroke and a fill value of the the shape within the them. If it's an ImageTheme, the paintComponent will draw an image instead.
    - Resize: within the top-most GUI class (Application), a ComponentAdapter we called ResizeWindowAdapter is created and sent the major components of the window: BoardPanel, NextPiecePanel, ControlPanel and ScorePanel.
      Within the componentResized method all of the panels are dynamically resized to become bigger or smaller and fit to their screen ratio. For example: BoardPanel is 8/10 of the height of the window while the ControlPanel takes the rest.
      However, this system isn't perfect and once the window is wide enough the ControlPanel will move to the right side of the window, this isn't intended.
      Additionally, BoardPanel and NextPiecePanel store the size at which to draw their blocks as state and this get's updated when they are resized to create a seamless resize.
      These panels only resize in multiples of 20 (the default block size) so there is no extra space within the grid system.
    - Control Key Icons & Sprite Sheet System: Key icons were implemented to make ControlPanel less text based and easier to interpret.
      We used a sprite sheet of keyboard icons from: https://dreammix.itch.io/keyboard-keys-for-ui
      The sprite sheet image is read into a SpriteSheet class and given information about tile size (16x16) to break the image into a grid.
      This allows us to use spriteSheet.getSprite(1, 2) to get a subimage within the sprite sheet at coordinates (1, 2)
      Within ControlPanel a 3x3 grid exists where Image JLabel(s) and a text JLabel are dynamically created within the cells and scaled using the resize system mentioned before. 




