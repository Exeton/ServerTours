package online.fireflower.server_tours.commands;

import online.fireflower.server_tours.ServerTourInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class STList extends STCommand {

    List<ServerTourInfo> serverTours;
    public static String chatColor;

    public STList(List<ServerTourInfo> serverTours){
        this.serverTours = serverTours;
    }

    @Override
    public void execute(String cmdInfo, Player sender, String[] args) {
        for (ServerTourInfo serverTourInfo : serverTours){
            sender.sendMessage(chatColor + serverTourInfo.name);
        }
    }
}
