package online.fireflower.server_tours.commands.tourCommands;

import online.fireflower.server_tours.Particles.ParticleEffects;
import online.fireflower.server_tours.ServerTourInfo;
import online.fireflower.server_tours.creation.IServerTourCRUD;
import org.bukkit.command.CommandSender;

public class STSetParticle extends STTourCommand {

    IServerTourCRUD serverTourCRUD;
    public static String invalidParticleName = "Invalid particle name";

    public STSetParticle(IServerTourCRUD serverTourCRUD){
        this.serverTourCRUD = serverTourCRUD;
    }

    @Override
    public void execute(CommandSender commandSender, ServerTourInfo serverTour, String[] args) {

        ParticleEffects particleEffect = ParticleEffects.fromName(args[0]);

        if (particleEffect == null){
            commandSender.sendMessage(invalidParticleName);
            return;
        }

        serverTour.particleType =  particleEffect;
        serverTourCRUD.update(serverTour);
    }
}
