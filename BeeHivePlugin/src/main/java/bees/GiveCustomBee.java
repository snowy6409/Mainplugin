package bees;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GiveCustomBee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player)
        {
            Player p = (Player) commandSender;
            if(p.hasPermission("Bee.give"))
            {
                ItemStack CustomBees = new ItemStack(Material.BEEHIVE);
                ItemMeta im = CustomBees.getItemMeta();
                im.setDisplayName(ChatColor.BLACK + "Coal Bees");
                List<String> lore = new ArrayList<String>();
                lore.add("Coal generating hive");
                im.setLore(lore);
                CustomBees.setItemMeta(im);
                CustomBees.setAmount(1);
                p.getInventory().addItem(CustomBees);
            }
        }



        return true;
    }
}
