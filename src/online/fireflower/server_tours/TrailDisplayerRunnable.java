package online.fireflower.server_tours;

import online.fireflower.server_tours.Particles.ParticleEffects;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class TrailDisplayerRunnable implements Runnable {

    List<ServerTourInfo> serverTours;

    public TrailDisplayerRunnable(List<ServerTourInfo> serverTours){
        this.serverTours = serverTours;
    }

    @Override
    public void run() {
        for(ServerTourInfo serverTourInfo : serverTours)
            for (Location location : serverTourInfo.particleLocatons){

                try{
                    sendParticlesForLocation(location, serverTourInfo);
                }catch (Exception e){

                    List<Player> activePlayers = serverTourInfo.getActivePlayers();
                    for (int i = activePlayers.size() - 1; i > -1; i--){
                        Player player = activePlayers.get(i);
                        if (!player.isOnline() || !player.getWorld().equals(location.getWorld()))
                            activePlayers.remove(player);
                    }

                    sendParticlesForLocation(location, serverTourInfo);
                }
            }
    }

    private void sendParticlesForLocation(Location location, ServerTourInfo serverTourInfo){
        if (serverTourInfo.getActivePlayers().size() > 0)
            serverTourInfo.particleType.display(0, 0, 0, 0F, 3, location, serverTourInfo.getActivePlayers());

    }
}
