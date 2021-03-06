package online.fireflower.server_tours.commands.tourCommands;

import online.fireflower.server_tours.ServerTourInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class STQuitTour extends STTourCommand {

    @Override
    public void execute(CommandSender commandSender, ServerTourInfo serverTour, String[] args) {

        if (args.length <= 0){
            commandSender.sendMessage("Usage /ST <tourName> quit Notch");
            return;
        }


        Player player = Bukkit.getPlayer(args[0]);
        if (player == null){
            commandSender.sendMessage("Player not found");
        }
        serverTour.removeActivePlayer(player);
    }
}
