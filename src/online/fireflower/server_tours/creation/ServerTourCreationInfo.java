package online.fireflower.server_tours.creation;

import online.fireflower.server_tours.Checkpoint;
import online.fireflower.server_tours.ServerTourInfo;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class ServerTourCreationInfo {

    Player player;
    String name;
    List<Location> locations = new LinkedList<>();
    List<Checkpoint> checkpoints = new LinkedList<>();

    public ServerTourCreationInfo(Player player, String name){
        this.player = player;
        this.name = name;
    }

    public void addLocation(Location location){
        locations.add(location);
    }

    public boolean isValidServerTour(){
        return true;
    }

    public ServerTourInfo createServerTourInfo(){

        ServerTourInfo serverTourInfo = new ServerTourInfo(name, checkpoints, locations);
        return serverTourInfo;
    }

}
