package online.fireflower.server_tours;

import online.fireflower.server_tours.commands.*;
import online.fireflower.server_tours.commands.PlayerOnTour.STQuit;
import online.fireflower.server_tours.commands.tourCommands.STHologramBook;
import online.fireflower.server_tours.commands.tourCommands.STSetParticle;
import online.fireflower.server_tours.commands.tourCommands.STStartTour;
import online.fireflower.server_tours.commands.tourCommands.STTourCommand;
import online.fireflower.server_tours.creation.FileServerTourCRUD;
import online.fireflower.server_tours.creation.IServerTourCRUD;
import online.fireflower.server_tours.creation.ServerTourCreationInfo;
import online.fireflower.server_tours.events.BookSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ServerTours extends JavaPlugin {

    public static ServerTours main;

    @Override
    public void onEnable() {

        main = this;

        IServerTourCRUD serverTourCRUD = new FileServerTourCRUD(this, "\\trails");

        saveDefaultConfig();
        Configuration config = getConfig();
        registerMessages(config);

        HashMap<UUID, ServerTourCreationInfo> serverTourCreationInfoHashMap = new HashMap<>();
        HashMap<String, ServerTourInfo> namesAndServerTours = new HashMap<>();
        List<ServerTourInfo> serverTours = serverTourCRUD.readAllServerTourInfos();
        for (ServerTourInfo serverTour : serverTours)
            namesAndServerTours.put(serverTour.name, serverTour);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerMovement(serverTourCreationInfoHashMap), this);
        pluginManager.registerEvents(new BookSign(namesAndServerTours, serverTourCRUD), this);

        //Server Commands
        HashMap<String, STCommand> serverTourCommands = new HashMap<>();
        serverTourCommands.put("create", new STCreate(serverTourCreationInfoHashMap));
        serverTourCommands.put("finish", new STFinish(serverTourCreationInfoHashMap, serverTours, namesAndServerTours, serverTourCRUD));
        serverTourCommands.put("quitcreation", new STQuitCreation(serverTourCreationInfoHashMap));
        serverTourCommands.put("quit", new STQuit(serverTours));
        serverTourCommands.put("list", new STList(serverTours));

        List<String> helpPlainText = config.getStringList("StHelpMessages");
        List<String> helpColorCoded = new LinkedList<>();
        for (String line : helpPlainText)
            helpColorCoded.add(ChatColor.translateAlternateColorCodes('&', line));
        serverTourCommands.put("help", new STHelp(helpColorCoded));



        //Id Commands
        HashMap<String, STTourCommand> idBasedCommands = new HashMap<>();

        STStartTour startTour = new STStartTour();

        idBasedCommands.put("start", startTour);
        idBasedCommands.put("show", startTour);
        idBasedCommands.put("trail", new STSetParticle(serverTourCRUD));
        idBasedCommands.put("holobook", getHologramBook(config));

        ServerToursCommand serverToursCommand = new ServerToursCommand(serverTourCommands, idBasedCommands, namesAndServerTours);
        this.getCommand("St").setExecutor(serverToursCommand);


        Runnable trailRunnable = new TrailDisplayerRunnable(serverTours);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, trailRunnable, 20L, 20L);//Idk which one is the delay
    }

    private STHologramBook getHologramBook(Configuration config){

        String loreBookName = ChatColor.translateAlternateColorCodes('&', config.getString("hologramBookName"));
        List<String> loreBookLorePlain = config.getStringList("hologramBookLore");
        List<String> loreBookLoreColor = new LinkedList<>();

        for (String line : loreBookLorePlain)
            loreBookLoreColor.add(ChatColor.translateAlternateColorCodes('&', line));

        return new STHologramBook(loreBookName, loreBookLoreColor);
    }

    //Yes I know, not very elegant
    private void registerMessages(Configuration config){

        STCreate.alreadyCreatingTourMessage = ChatColor.translateAlternateColorCodes('&', config.getString("AlreadyCreatingMessage"));
        STCreate.nowCreatingTourMessage = ChatColor.translateAlternateColorCodes('&', config.getString("YouHaveStartedCreatingMessage"));
        //STCreateCheckpoint
        STFinish.notCreatingTourMessage = ChatColor.translateAlternateColorCodes('&', config.getString("NotCreatingCourse"));
        STFinish.tourCreatedMessage = ChatColor.translateAlternateColorCodes('&', config.getString("SuccessfulCourseCreation"));

        STQuitCreation.notCreatingTourMessage = ChatColor.translateAlternateColorCodes('&', config.getString("NotCreatingCourse"));
        STQuitCreation.stoppedCreationMessage = ChatColor.translateAlternateColorCodes('&', config.getString("QuitCreation"));

        STQuit.notInTourMessage = ChatColor.translateAlternateColorCodes('&', config.getString("NotInTour"));;
        STQuit.tourQuitMessage = ChatColor.translateAlternateColorCodes('&', config.getString("TourQuit"));;

        ServerToursCommand.tourNotFound = ChatColor.translateAlternateColorCodes('&', config.getString("CourseNotFound"));;

        STList.chatColor = ChatColor.translateAlternateColorCodes('&', config.getString("STListColorCodePrefix"));

        BookSign.checkpointCreatedMessage = ChatColor.translateAlternateColorCodes('&', config.getString("checkpointCreatedMessage"));
    }
}
