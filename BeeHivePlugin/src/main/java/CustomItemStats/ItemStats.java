package CustomItemStats;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStats {
    double CurrentDurability = -1;
    double MaxDurability = -1;
    String Name = "";
    double Damage = -1;


    public double getCurrentDurability(ItemStack is){
        double dur= 0;

        ItemMeta im = is.getItemMeta();
        List<String> Lore = im.getLore();

        for(String s : Lore)
        {
            if(s.toLowerCase().contains("durability"))
            {
                dur = Double.parseDouble(s.substring(s.indexOf(']')+1,s.indexOf('/')-1));
                break;
            }
        }
        return dur;
    }
    public double getMaxDurability(ItemStack is)
    {
        double durmax = 0;

        ItemMeta im = is.getItemMeta();
        List<String> Lore = im.getLore();

        for(String s : Lore)
        {
            if(s.toLowerCase().contains("durability"))
            {
                durmax = Double.parseDouble(s.substring(s.indexOf('/')));
                break;
            }
        }
        return durmax;
    }

    public ItemStack setCurrentDurability(ItemStack is,double value)
    {
        ItemMeta im = is.getItemMeta();
        List<String> Lore = im.getLore();
        int tmpit = 0;
        for(String s : Lore)
        {
            if(s.toLowerCase().contains("durability"))
            {
                break;
            }
            tmpit += 1;
        }2
        String DurabilityLine = Lore.get(tmpit);
        String New = DurabilityLine.substring(0,']') + " " + getCurrentDurability(is) + "/" + getMaxDurability(is);
        Lore.set(tmpit,New);
        im.setLore(Lore);
        is.setItemMeta(im);
        if(getCurrentDurability(is)<=0)
        {
            is.setAmount(0);
        }
        return is;
    }


}
