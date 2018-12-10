package online.fireflower.server_tours;

import online.fireflower.server_tours.Particles.ParticleEffects;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class ServerTourInfo {

    public String name;
    public List<Checkpoint> checkpoints;
    private List<Player> activePlayers;
    public List<Location> particleLocatons;
    public ParticleEffects particleType = ParticleEffects.FLAME;

    public ServerTourInfo(String name, List<Checkpoint> checkpoints, List<Location> particleLocatons, ParticleEffects particleType){
        this.name = name;
        this.checkpoints = checkpoints;
        this.particleLocatons = particleLocatons;
        this.particleType = particleType;
        activePlayers = new LinkedList<>();
    }

    public ServerTourInfo(String name, List<Checkpoint> checkpoints, List<Location> particleLocatons){
        this.name = name;
        this.checkpoints = checkpoints;
        this.particleLocatons = particleLocatons;
        activePlayers = new LinkedList<>();
    }

    public List<Player> getActivePlayers(){
        return activePlayers;
    }

    public void addActivePlayer(Player player){

        for (Checkpoint checkpoint : checkpoints)
            checkpoint.hologram.getVisibilityManager().showTo(player);

        activePlayers.add(player);
    }

    public boolean hasActivePlayer(Player player){
        return activePlayers.contains(player);
    }

    public void removeActivePlayer(Player player){

        if (!activePlayers.contains(player))
            return;

        for (Checkpoint checkpoint : checkpoints)
            checkpoint.hologram.getVisibilityManager().hideTo(player);

        activePlayers.remove(player);
    }
}
