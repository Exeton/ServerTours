package online.fireflower.server_tours.commands;

import online.fireflower.server_tours.ServerTourInfo;
import online.fireflower.server_tours.commands.tourCommands.STTourCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ServerToursCommand implements CommandExecutor {

    HashMap<String, STCommand> serverTourCommands; //st <Command>
    HashMap<String, STTourCommand> idBasedCommands;//st <id> <Command>
    HashMap<String, ServerTourInfo> serverTours;

    public static String tourNotFound;

    public ServerToursCommand(HashMap<String, STCommand> serverTourCommands, HashMap<String, STTourCommand> idBasedCommands, HashMap<String, ServerTourInfo> serverTours){
        this.serverTourCommands = serverTourCommands;
        this.idBasedCommands = idBasedCommands;
        this.serverTours = serverTours;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        boolean notEnoughArgsForIdCommand = strings.length < 2;
        if (strings.length == 0 || (!serverTourCommands.containsKey(strings[0].toLowerCase()) && notEnoughArgsForIdCommand)){
            commandSender.sendMessage("Invalid usage");
            return true;
        }

        String commandArg = strings[0].toLowerCase();

        if (serverTourCommands.containsKey(commandArg)){
            if (!(commandSender instanceof Player)){
                commandSender.sendMessage("You must be a player to use this command");
            }

            STCommand stCommand = serverTourCommands.get(commandArg);
            String[] commandInfo = new String[strings.length - 1];
            for (int i = 1; i < strings.length; i++){
                commandInfo[i - 1] = strings[i];
            }
            stCommand.execute("",(Player)commandSender, commandInfo);
        }
        else{
            STTourCommand stCommand = idBasedCommands.get(strings[1].toLowerCase());//Change command arg to next arg
            if (stCommand == null){
                commandSender.sendMessage(ChatColor.GRAY + "Use /st help for command usage");
                return true;
            }

            String[] args = new String[strings.length - 2];
            for (int i = 2; i < strings.length; i++)
                args[i - 2] = strings[i];


            ServerTourInfo serverTourInfo = serverTours.get(strings[0]);
            if (serverTourInfo == null){
                commandSender.sendMessage(tourNotFound.replace("%id%", strings[0]));
                return true;
            }

            stCommand.execute(commandSender, serverTourInfo, args);
        }
        return true;
    }
}
