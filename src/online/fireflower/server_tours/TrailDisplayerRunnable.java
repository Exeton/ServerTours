package online.fireflower.server_tours;

import online.fireflower.server_tours.Particles.ParticleEffects;
import org.bukkit.Location;

import java.util.List;

public class TrailDisplayerRunnable implements Runnable {

    List<ServerTourInfo> serverTours;

    public TrailDisplayerRunnable(List<ServerTourInfo> serverTours){
        this.serverTours = serverTours;
    }

    @Override
    public void run() {
        for(ServerTourInfo serverTourInfo : serverTours)
            for (Location location : serverTourInfo.particleLocatons)
                if (serverTourInfo.getActivePlayers().size() > 0)
                    serverTourInfo.particleType.display(0, 0, 0, 0F, 3, location, serverTourInfo.getActivePlayers());
    }
}
