# PingKick
Automatically kicks player with a high ping

## Commands
- /pingkick info - Returns info about the current config
- /pingkick max `<ping>` - Sets the max ping in milliseconds
- /pingkick command `<command>` - Sets the command used when a player goes over that limit. Will replace {name}, {ip}, {uuid}, {ping}, {max} with their respective values when executed
- /pingkick period `<period>` - Amount of ticks between each ping check (20 ticks are 1 second)

## Permissions
- pingkick.info - /pingkick info
- pingkick.max - /pingkick max `<ping>`
- pingkick.command - /pingkick command `<command>`
- pingkick.period - /pingkick period `<period>`
- pingkick.bypass - Bypass the ping restriction

#### Special thanks to [@etcroot](https://www.spigotmc.org/members/707841/) for letting me continue his project