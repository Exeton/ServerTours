package online.fireflower.server_tours.commands.PlayerOnTour;

import online.fireflower.server_tours.ServerTourInfo;
import online.fireflower.server_tours.commands.STCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class STQuit extends STCommand {

    List<ServerTourInfo> serverTours;
    public static String tourQuitMessage;
    public static String notInTourMessage;

    public STQuit(List<ServerTourInfo> serverTours){
        this.serverTours = serverTours;
    }

    @Override
    public void execute(String cmdInfo, Player cmdSender, String[] args) {

        for (ServerTourInfo serverTour : serverTours){
            if (serverTour.hasActivePlayer(cmdSender)){
                serverTour.removeActivePlayer(cmdSender);
                cmdSender.sendMessage(tourQuitMessage);
                return;
            }
        }

        cmdSender.sendMessage(notInTourMessage);
    }
}
