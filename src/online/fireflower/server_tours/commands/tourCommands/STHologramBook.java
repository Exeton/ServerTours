package online.fireflower.server_tours.commands.tourCommands;

import online.fireflower.server_tours.ServerTourInfo;
import online.fireflower.server_tours.commands.STCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class STHologramBook extends STTourCommand {

    private String bookName;
    private List<String> bookLore;

    public STHologramBook(String bookName, List<String> bookLore){
        this.bookLore = bookLore;
        this.bookName = bookName;
    }

    @Override
    public void execute(CommandSender commandSender, ServerTourInfo serverTour, String[] args) {

        Bukkit.getLogger().info("Giving book");

        if (!(commandSender instanceof Player))
            return;

        Bukkit.getLogger().info("Book given");

        Player player = (Player)commandSender;
        player.getInventory().addItem(createHologramBook(serverTour.name));
    }

    private ItemStack createHologramBook(String tourName){

        ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setDisplayName(bookName + " TourName=" + tourName);
        itemMeta.setLore(bookLore);
        stack.setItemMeta(itemMeta);

        return stack;
    }


}
