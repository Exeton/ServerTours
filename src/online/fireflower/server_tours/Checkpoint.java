package online.fireflower.server_tours;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;

import java.util.List;

public class Checkpoint {

    public List<String> hologramText;
    public Location location;
    public Hologram hologram;

    public Checkpoint(List<String> hologramText, Location location){
        this.hologramText = hologramText;
        this.location = location;

        hologram = HologramsAPI.createHologram(ServerTours.main, location);
        hologram.getVisibilityManager().setVisibleByDefault(false);

        for (String line : hologramText)
            hologram.appendTextLine(line);
    }
}
