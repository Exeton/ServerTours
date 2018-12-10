package online.fireflower.server_tours;

import online.fireflower.server_tours.creation.ServerTourCreationInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerMovement implements Listener {

    HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfoMap;

    public PlayerMovement(HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfo){
        this.serverTourCreationInfoMap = serverTourCreationInfo;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent playerMoveEvent){
        if (!serverTourCreationInfoMap.containsKey(playerMoveEvent.getPlayer().getUniqueId())){
            return;
        }

        ServerTourCreationInfo serverTourCreationInfo = serverTourCreationInfoMap.get(playerMoveEvent.getPlayer().getUniqueId());
        serverTourCreationInfo.addLocation(playerMoveEvent.getTo());
    }
}
