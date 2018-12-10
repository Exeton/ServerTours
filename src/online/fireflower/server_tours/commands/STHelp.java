package online.fireflower.server_tours.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class STHelp extends STCommand {

    List<String> commandMessages;

    public STHelp(List<String> commandMessages){
        this.commandMessages = commandMessages;
    }

    @Override
    public void execute(String cmdInfo, Player sender, String[] args) {
        for (String message : commandMessages){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}
