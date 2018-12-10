package online.fireflower.server_tours.creation;

import online.fireflower.server_tours.Checkpoint;
import online.fireflower.server_tours.Particles.ParticleEffects;
import online.fireflower.server_tours.ServerTourInfo;
import online.fireflower.server_tours.ServerTours;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileServerTourCRUD implements IServerTourCRUD {

    String trailsSubPath;
    ServerTours main;

    public FileServerTourCRUD(ServerTours main, String trailsSubPath){
        this.main = main;
        this.trailsSubPath =  trailsSubPath;
    }

    private String getTrailsFolder(){
        return main.getDataFolder().getAbsolutePath() + trailsSubPath;
    }

    @Override
    public void create(ServerTourInfo serverTourInfo) {
        File configFile = new File(getTrailsFolder(), serverTourInfo.name + ".yml");
        if (!configFile.exists()){

            File rootFolder = new File(getTrailsFolder());
            rootFolder.mkdirs();

            try{
                PrintWriter writer = new PrintWriter(configFile);
                writer.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }

        config.set("name", serverTourInfo.name);
        config.set("particleType", serverTourInfo.particleType.toString());

        List<Map<String, Object>> locations = new LinkedList<>();
        for (Location loc : serverTourInfo.particleLocatons)
            locations.add(loc.serialize());
        config.set("locations", locations);


        for (int i = 0; i < serverTourInfo.checkpoints.size(); i++){
            saveCheckPoint(serverTourInfo.checkpoints.get(i), config, "checkpoints.checkpoint" + Integer.toString(i));
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save checkpoints
    }

    private void saveCheckPoint(Checkpoint checkpoint, Configuration config, String path){

        config.set(path + ".location",  checkpoint.location.serialize());
        config.set(path + ".text", checkpoint.hologramText);
    }

    @Override
    public void delete(String name) {
        File config = new File(getTrailsFolder(), name + ".yml");
        config.delete();
    }

    @Override
    public void update(ServerTourInfo serverTourInfo) {
        delete(serverTourInfo.name);
        create(serverTourInfo);
    }

    @Override
    public ServerTourInfo readServerTourInfo(String name) {

        File configFile = new File(getTrailsFolder(), name + ".yml");
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            return null;
        }

        String tourName = config.getString("name");
        ParticleEffects particleEffect = ParticleEffects.fromName(config.getString("particleType"));

        List<Map<String, Object>> locationsMap = (List<Map<String, Object>>)config.getList("locations");
        List<Location> locations = new LinkedList<>();

        for (Map<String, Object> locationMap : locationsMap)
            locations.add(Location.deserialize(locationMap));


        List<Checkpoint> checkpoints = new LinkedList<>();
        if (config.getConfigurationSection("checkpoints") != null){
            for (String key: config.getConfigurationSection("checkpoints").getKeys(false))
                checkpoints.add(readCheckpoint(config, "checkpoints." + key));
        }
        return new ServerTourInfo(tourName, checkpoints,locations, particleEffect);
    }

    private Checkpoint readCheckpoint(Configuration config, String path){

        return new Checkpoint(config.getStringList(path + ".text"),
                Location.deserialize(config.getConfigurationSection(path + ".location").getValues(true)));
    }

    @Override
    public List<ServerTourInfo> readAllServerTourInfos() {

        List<ServerTourInfo> serverTours = new LinkedList<>();

        File dirFolder = new File(getTrailsFolder());
        if (!dirFolder.exists())
            return serverTours;

        for (File file : dirFolder.listFiles()){
            serverTours.add(readServerTourInfo(FilenameUtils.removeExtension(file.getName())));
        }

        return serverTours;
    }
}
