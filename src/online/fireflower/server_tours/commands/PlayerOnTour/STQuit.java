package online.fireflower.server_tours.commands.PlayerOnTour;

import online.fireflower.server_tours.ServerTourInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class STQuit implements CommandExecutor {

    List<ServerTourInfo> serverTours;
    public static String tourQuitMessage;
    public static String notInTourMessage;

    public STQuit(List<ServerTourInfo> serverTours){
        this.serverTours = serverTours;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player)commandSender;
        boolean quitTour = false;

        for (ServerTourInfo serverTour : serverTours){
            if (serverTour.hasActivePlayer(player)){
                serverTour.removeActivePlayer(player);
                quitTour = true;
            }
        }

        if (quitTour)
            player.sendMessage(tourQuitMessage);
        else
            player.sendMessage(notInTourMessage);

        return true;
    }
}
