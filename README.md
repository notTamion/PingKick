# PingKick
Automatically kicks players with a too high average ping

## Usage
- /setpingthreshold <ping in ms> - Sets the max ping threshold. Set to 0 to disable
- /setkickcommand <command> - Sets Command that gets executed when a players ping goes over the threshold (All instances of "{player}" in the command are going to be replaced with the player name)
- /setcheckfrequency <frequency in ticks> - How often the ping of players gets checked

## Default values
- pingthreshold - 0 (disabled)
- kickcommand - kick {player} High Ping
- checkfrequency - 100 (5 seconds)