package bees;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BeeInventory implements InventoryHolder {
    private final Inventory inv;
    public BeeInventory() {
        inv = Bukkit.createInventory(this, 9, "Bee GUI");
    }
    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void initializeItems(int coalamount) {
        inv.setItem(0,createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ",1,""));

        inv.setItem(1,createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ",1,""));

        inv.setItem(2,createGuiItem(Material.GREEN_CONCRETE, "Collect Coal",1, "§aClick here to collect coal", ""));
        inv.setItem(3,createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ",1,""));
        inv.setItem(4,createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ",1,""));
        inv.setItem(5,createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ",1,""));

        if(coalamount > 0) {
            inv.setItem(6,createGuiItem(Material.COAL, "§bCoal Collected",coalamount, "",""));
        }
        inv.setItem(7,createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ",1,""));
        inv.setItem(8,createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ",1,""));

    }
    private ItemStack createGuiItem(Material material, String name, int amount,String...lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {

            metalore.add(lorecomments);

        }
        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }
    public void openInventory(Player p) {
        p.openInventory(inv);
    }

}
