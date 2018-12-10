package online.fireflower.server_tours.commands;

import online.fireflower.server_tours.ServerTourInfo;
import online.fireflower.server_tours.creation.IServerTourCRUD;
import online.fireflower.server_tours.creation.ServerTourCreationInfo;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class STFinish extends STCommand{

    HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfoMap;
    HashMap<String, ServerTourInfo> namesAndServerTours;
    List<ServerTourInfo> serverTours;
    IServerTourCRUD serverTourCRUD;

    public static String notCreatingTourMessage;
    public static String tourCreatedMessage;

    public STFinish(HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfoMap, List<ServerTourInfo> serverTours, HashMap<String, ServerTourInfo> namesAndServerTours, IServerTourCRUD serverTourCRUD){
        this.serverTourCreationInfoMap = serverTourCreationInfoMap;
        this.serverTours = serverTours;
        this.namesAndServerTours = namesAndServerTours;
        this.serverTourCRUD = serverTourCRUD;
    }

    @Override
    public void execute(String cmdInfo, Player cmdSender, String[] args) {

        ServerTourCreationInfo serverTourCreationInfo = serverTourCreationInfoMap.get(cmdSender.getUniqueId());

        if (serverTourCreationInfo == null) {
            cmdSender.sendMessage(notCreatingTourMessage);
        }
        else{
            if (!serverTourCreationInfo.isValidServerTour()){
                cmdSender.sendMessage("Invalid server tour [You should not get this message]");
                return;
            }

            ServerTourInfo serverTourInfo = serverTourCreationInfo.createServerTourInfo();
            serverTours.add(serverTourInfo);
            namesAndServerTours.put(serverTourInfo.name, serverTourInfo);
            serverTourInfo.addActivePlayer(cmdSender);

            serverTourCreationInfoMap.remove(cmdSender.getUniqueId());//Stop them from continuing editing
            serverTourCRUD.create(serverTourInfo);

            cmdSender.sendMessage(tourCreatedMessage.replace("%id%", serverTourInfo.name));
        }

    }
}
