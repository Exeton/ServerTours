package online.fireflower.server_tours.events;

import online.fireflower.server_tours.Checkpoint;
import online.fireflower.server_tours.ServerTourInfo;
import online.fireflower.server_tours.creation.IServerTourCRUD;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;

import java.util.*;

public class BookSign implements Listener {

    HashMap<String, ServerTourInfo> namesAndServerTours;
    IServerTourCRUD serverTourCRUD;

    public static String checkpointCreatedMessage;

    public BookSign(HashMap<String, ServerTourInfo> namesAndServerTours, IServerTourCRUD serverTourCRUD){
        this.namesAndServerTours = namesAndServerTours;
        this.serverTourCRUD = serverTourCRUD;
    }

    @EventHandler
    public void onBookEdit(PlayerEditBookEvent event){

        if (!event.isSigning())
            return;

        String tourName = event.getPreviousBookMeta().getDisplayName().split("TourName=")[1];
        ServerTourInfo serverTour = namesAndServerTours.get(tourName);

        if (serverTour != null){

            Player authour = Bukkit.getPlayer(event.getNewBookMeta().getAuthor());
            String appendedText = ChatColor.translateAlternateColorCodes('&',String.join("", event.getNewBookMeta().getPages()));
            List<String> hologramLines = Arrays.asList(appendedText.split("/n"));

            Checkpoint checkpoint = new Checkpoint(hologramLines, authour.getLocation().add(0,2,0));
            serverTour.checkpoints.add(checkpoint);
            checkpoint.hologram.getVisibilityManager().showTo(authour);

            serverTourCRUD.update(serverTour);
        }
    }
}
