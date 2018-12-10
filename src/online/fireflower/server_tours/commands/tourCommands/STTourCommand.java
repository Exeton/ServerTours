package online.fireflower.server_tours.commands.tourCommands;

import online.fireflower.server_tours.ServerTourInfo;
import org.bukkit.command.CommandSender;

public abstract class STTourCommand {
    public abstract void execute(CommandSender commandSender, ServerTourInfo serverTour, String[] args);
}
