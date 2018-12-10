package online.fireflower.server_tours.commands;

import online.fireflower.server_tours.creation.ServerTourCreationInfo;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class STCreate extends STCommand{

    HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfoMap;
    public static String alreadyCreatingTourMessage;
    public static String nowCreatingTourMessage;
    public static String usage = "Usage: /st Create id";

    public STCreate( HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfoMap){
        this.serverTourCreationInfoMap = serverTourCreationInfoMap;
    }

    @Override
    public void execute(String cmdInfo, Player cmdSender, String[] args) {

        ServerTourCreationInfo serverTourCreationInfo = serverTourCreationInfoMap.get(cmdSender.getUniqueId());

        if (args.length < 1){
            cmdSender.sendMessage(usage);
            return;
        }

        if (serverTourCreationInfo == null) {
            serverTourCreationInfo = new ServerTourCreationInfo(cmdSender, args[0]);
             serverTourCreationInfoMap.put(cmdSender.getUniqueId(), serverTourCreationInfo);
            cmdSender.sendMessage(nowCreatingTourMessage.replace("%id%", args[0]));
        }
        else
            cmdSender.sendMessage(alreadyCreatingTourMessage);

    }
}
