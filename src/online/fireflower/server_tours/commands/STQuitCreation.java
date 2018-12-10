package online.fireflower.server_tours.commands;

import online.fireflower.server_tours.creation.ServerTourCreationInfo;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class STQuitCreation extends STCommand{

    HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfoMap;

    public static String notCreatingTourMessage;
    public static String stoppedCreationMessage;

    public STQuitCreation(HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfoMap){
        this.serverTourCreationInfoMap = serverTourCreationInfoMap;
    }

    @Override
    public void execute(String cmdInfo, Player cmdSender, String[] args) {

        ServerTourCreationInfo serverTourCreationInfo = serverTourCreationInfoMap.get(cmdSender.getUniqueId());

        if (serverTourCreationInfo == null)
            cmdSender.sendMessage(notCreatingTourMessage);
        else{
            serverTourCreationInfoMap.remove(cmdSender.getUniqueId());
            cmdSender.sendMessage(stoppedCreationMessage);
        }

    }
}
