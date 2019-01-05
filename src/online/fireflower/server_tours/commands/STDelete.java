package online.fireflower.server_tours.commands;

import online.fireflower.server_tours.ServerTourInfo;
import online.fireflower.server_tours.commands.tourCommands.STTourCommand;
import online.fireflower.server_tours.creation.IServerTourCRUD;
import online.fireflower.server_tours.creation.ServerTourCreationInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class STDelete extends STTourCommand {

    HashMap<String, ServerTourInfo> namesAndServerTours;
    List<ServerTourInfo> serverTours;
    IServerTourCRUD serverTourCRUD;

    public static String tourDeletedMessage;

    public STDelete(List<ServerTourInfo> serverTours, HashMap<String, ServerTourInfo> namesAndServerTours, IServerTourCRUD serverTourCRUD){

        this.serverTours = serverTours;
        this.namesAndServerTours = namesAndServerTours;
        this.serverTourCRUD = serverTourCRUD;
    }

    @Override
    public void execute(CommandSender commandSender, ServerTourInfo serverTour, String[] args) {
        serverTourCRUD.delete(serverTour.name);
        namesAndServerTours.remove(serverTour.name);
        serverTours.remove(serverTour);
        commandSender.sendMessage(tourDeletedMessage);
    }
}
