# Game 2048
## Introduction
2048 is a logic game played usually on 4x4 board. The board contains
blocks of various values. In each turn, the player choses the direction
(up, down, left, right) in which all blocks on the board will move.
During the turn, a collision of two blocks can happen. If they are
of same value, they will be merged together to form single block
of doubled value. After the turn, a random spot on the board is chosen
and a new block with value of 2 (rarely 4) will spawn on that spot.
At the beginning, there are two blocks of values 2 (rarely 4).
The player is winner if he manages to combine blocks into a block with the
value of 2048. If there is no direction, in which there would be a movement
of at least one block, then the player is declared as a loser.

This is its implementation in Java.

##Supported Platforms
This program can be run on any platform, which supports **Java 1.8** with
at least **Java 1.8** installed.

##Download
You can download source code directly as release or you can clone
the repository by yourself and build it using included ant buildfile.
JavaDoc generated documentation can be accessed online by clicking
[this](http://www.zereges.cz/2048Java/doc/) link 

You can download binaries, containing everything required to run the program
from [this](http://www.zereges.cz/2048Java/2048.zip) mirror.

##Installation
The program does not need to be installed at all.

##Build
Program can be builded by running ant from root directory  
`ant` and `ant build-jar`

##Program Start
Program can be started by executing *2048.jar* directly, or using ant
(`ant run-jar`).

##Controls
###Player Profile Window
After the program starts, a window for choosing player profile will appear.
Using selection box, you can choose from existing profiles or you can create a
new one by typing desired name into the field and then clicking
*Create new profile* button. If you wish to delete all the contents of currently
selected player profile click on the *Delete profile button* a confirmation
window will pop up, warning you, that this is the last chance to cancel deletion
of the profile. After the deletion proceeds, the profile can not be restored.
After the player clicks *Load profile & Start game* button, the currently
selected profile will load and the game will start.

###Game Window
Once the game loads a new window will appear, reflecting current situation on
the game field. In the top left corner, there is a score label showing player's
current score in the game. If player manages to win the game a *(Won)* will
appear next to the score points, indicating, that player is now playing for
higher score or he wants to obtain some achievements. Once player wins the game,
he can not lose due to lack of next move. If player loses the game before having
**2048** block, a message informing him about such situation will appear, and
the player is forced to restart the game.

In menu bar, there are three buttons, *Show Statistics* will open statistics
window, *Show Achievements* will bring up achievements window and finally
*Restart* button restarts current game progress. Current statistics, however,
will be preserved.

Player controls the game using the keyboard. *Arrow keys* performs a turn in
selected direction (Left arrow will perform left move and so on). Player may
restart the game using *R* button, which has the same functionality as *Restart*
button in the menu.

###Statistics Window
In statistics window, player can look through various statistics collected
during his playthrough. In *Current Stats* tab, there are statistics regarding
current gaming session. In *Global Stats* tab, player can find statistics
collecting for the life of the profile.

###Achievements Window
This window will reveal all achievements available in the game. Completed
achievements have *green* background, while yet uncompleted have *red*
background color. Achievements are stored in player profile, and achievement
progress won't reset after player exits the game. They will, however, be deleted
with deletion of the player profile.

The game is closed when player closes either *Player Profile Window* or
 *Game Window*, even if statistics or achievements window is opened.


Written by Filip Kliber (zereges<at>gmail.com) in 2014/2015 as Semester Assignment  
Link to [GitHub](https://github.com/Zereges/2048Java) repository.
