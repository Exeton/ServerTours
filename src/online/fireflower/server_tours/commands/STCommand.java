package online.fireflower.server_tours.commands;

import org.bukkit.entity.Player;

public abstract class STCommand {

    public abstract void execute(String cmdInfo, Player target, String[] args);

}
