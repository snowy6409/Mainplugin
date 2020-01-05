package bees;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player)
        {
            Player p = (Player) commandSender;
            /*
            if(p.hasPermission("Bee.test"))
            {
               p.sendMessage("Type-> " + p.getInventory().getItemInMainHand().getType());
               p.sendMessage("Custom Name-> " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
               HeadDatabaseAPI api = new HeadDatabaseAPI();
               ItemStack is = api.getItemHead("929");
                p.sendMessage("Custom Name Internal-> " + is.getItemMeta().getDisplayName());
                p.sendMessage("Evaluation -> " + (is.getItemMeta().getDisplayName().equals( p.getInventory().getItemInMainHand().getItemMeta().getDisplayName())));
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatColor.DARK_BLUE + "Water Core");
                is.setItemMeta(im);
                p.getInventory().addItem(is);
            }

             */
            ItemStack is = new ItemStack(Material.TNT);
            ItemMeta meta = is.getItemMeta();
            meta.setDisplayName("mining charges");
            is.setItemMeta(meta);
            is.setAmount(64);
            p.getInventory().addItem(is);


        }



        return true;
    }
}
