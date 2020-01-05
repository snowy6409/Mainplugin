package bees;

import me.arcaniax.hdb.Main;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Events implements Listener {

    BeePlugin plugin;
    BeeInventory bi;
    public Events(BeePlugin jp)
    {
        plugin = jp;
    }

    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent e)
    {
        if(e.getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("coal bees"))
        {
            BlockData bd = e.getBlockPlaced().getBlockData();

            if(plugin.getConfig().getString("Hives." + e.getPlayer().getDisplayName()+".coal") != null)
            {
                List<String> locations = plugin.getConfig().getStringList("Hives."+e.getPlayer().getDisplayName()+".coal");
                Location l = e.getBlock().getLocation();
                locations.add(l.getWorld().getName()+";"+l.getX()+";"+l.getY()+";"+l.getZ()+";1");
                plugin.getConfig().set("Hives."+e.getPlayer().getDisplayName()+".coal",locations);
            }
            else
            {
                List<String> locations = new ArrayList<String>();
                Location l = e.getBlock().getLocation();
                locations.add(l.getWorld().getName()+";"+l.getX()+";"+l.getY()+";"+l.getZ()+";1");
                plugin.getConfig().set("Hives."+e.getPlayer().getDisplayName()+".coal",locations);
            }
            e.getPlayer().sendMessage("New Coal Nest Registered");
            plugin.saveConfig();
        }
    }

    @EventHandler
    public void onCraftInterrupt(PrepareItemCraftEvent event)
    {
        if(event.getRecipe() != null) {
            if (event.getRecipe().getResult().getType() == Material.TRIPWIRE_HOOK) {
                int tmp = 0;
                ItemStack[] Matrix = event.getInventory().getMatrix();
                HeadDatabaseAPI api = new HeadDatabaseAPI();
                ItemStack is = api.getItemHead("929");
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatColor.DARK_BLUE + "Water Core");
                is.setItemMeta(im);
                if(Matrix[7].getItemMeta().getDisplayName().equals(is.getItemMeta().getDisplayName())){
                    ItemStack WaterKey = new ItemStack(Material.TRIPWIRE_HOOK);
                    ItemMeta imeta = WaterKey.getItemMeta();
                    imeta.setDisplayName(ChatColor.DARK_BLUE + "Level 1 water key");
                    WaterKey.setItemMeta(imeta);
                    event.getInventory().setResult(WaterKey);
                }
                else{
                    ItemStack BasicKey = new ItemStack(Material.TRIPWIRE_HOOK);
                    ItemMeta imeta = BasicKey.getItemMeta();
                    imeta.setDisplayName("Level 1 dungeon key");
                    BasicKey.setItemMeta(imeta);
                    event.getInventory().setResult(BasicKey);
                }

            }
        }
    }


    @EventHandler
    public void onPlayerInteractHive(PlayerInteractEvent e)
    {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            List<String> CoalBees = plugin.getConfig().getStringList("Hives."+e.getPlayer().getDisplayName()+".coal");
            boolean ShowGUI = false;
            Location l = e.getClickedBlock().getLocation();
            for(String s : CoalBees)
            {
                String[] split = s.split(";");
                if(split[0].equals( l.getWorld().getName()) && Double.parseDouble(split[1]) == l.getX() && Double.parseDouble(split[2]) == l.getY() && Double.parseDouble(split[3]) == l.getZ())
                {
                BeeInventory bi = new BeeInventory();
                bi.initializeItems(Integer.parseInt(split[4]));
                bi.openInventory(e.getPlayer());
                }
            }

//            //if(e.getClickedBlock().getMetadata())
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getView().getTitle().toLowerCase().contains("bee gui")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            // verify current item is not null
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            // Using slots click is a best option for your inventory click's
            if (e.getRawSlot() == 2)
            {
                ItemStack is = new ItemStack(Material.COAL,e.getInventory().getItem(6).getAmount());
                e.getInventory().getItem(6).setAmount(0);
                p.getInventory().addItem(is);
            }
        }
    }
    Beehive b;

    @EventHandler
    public void move(PlayerMoveEvent event)
    {
        if(event.getPlayer().getLocation().getWorld().getName().contains( "communistutopia"))
        {


            Player p = event.getPlayer();
            Location l = p.getLocation();
            if(l.getX() > 772 && l.getX() < 774 && l.getZ() > 265 && l.getZ() < 267 && l.getY() > 38 && l.getY() < 40)
            {
                if(p.getInventory().getItemInMainHand().getType() == Material.TRIPWIRE_HOOK && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().toLowerCase().contains("water key"))
                {
                    p.sendMessage("The water begins to warp around you");
                    p.getInventory().getItemInMainHand().setAmount(0);
                    p.performCommand("dxl play Water_Dungeon");
                }
                else
                {
                    p.sendMessage("There is no reaction, perhaps you need a special item");
                    p.teleport(new Location(l.getWorld(),l.getX(),l.getY()+9,l.getZ()));
                }
            }
        }
        else if(event.getPlayer().getLocation().getWorld().getName().toLowerCase().contains("world"))
        {
            Player p = event.getPlayer();
            Location l = p.getLocation();
            Location pBlockLoc = l.getBlock().getLocation();
            Location BlockBelow = pBlockLoc;
            BlockBelow.setY(BlockBelow.getY());
            if(BlockBelow.getBlock().getType() == Material.STONECUTTER)
            {
                p.damage(2);
            }
        }
    }




    @EventHandler
    public void PlaceThing(BlockPlaceEvent event)
    {
        if(event.getBlock()!= null)
        {
            if(event.getBlock().getType() == Material.TNT && event.getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("mining charges"))
            {
                Location l = event.getBlockPlaced().getLocation();

                switch (event.getPlayer().getFacing())
                {

                    /*



                     */
                    case WEST:
                            for(int i =0; i < 3; i++)
                            {
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY()+1,l.getBlockZ()-1).breakNaturally();
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY()+1,l.getBlockZ()).breakNaturally();
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY()+1,l.getBlockZ()+1).breakNaturally();
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY(),l.getBlockZ()-1).breakNaturally();
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY(),l.getBlockZ()).setType(Material.AIR);
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY(),l.getBlockZ()+1).breakNaturally();
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY()-1,l.getBlockZ()-1).breakNaturally();
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY()-1,l.getBlockZ()).breakNaturally();
                                l.getWorld().getBlockAt(l.getBlockX()-i,l.getBlockY()-1,l.getBlockZ()+1).breakNaturally();
                            }
                        break;
                    case EAST:
                        for(int i =0; i < 3; i++)
                        {
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY()+1,l.getBlockZ()-1).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY()+1,l.getBlockZ()).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY()+1,l.getBlockZ()+1).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY(),l.getBlockZ()-1).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY(),l.getBlockZ()).setType(Material.AIR);
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY(),l.getBlockZ()+1).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY()-1,l.getBlockZ()-1).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY()-1,l.getBlockZ()).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+i,l.getBlockY()-1,l.getBlockZ()+1).breakNaturally();
                        }
                        break;
                    case NORTH:
                        for(int i =0; i < 3; i++)
                        {
                            l.getWorld().getBlockAt(l.getBlockX()-1,l.getBlockY()+1,l.getBlockZ()-i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()-1,l.getBlockY()-1,l.getBlockZ()-i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()-1,l.getBlockY(),l.getBlockZ()-i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY()+1,l.getBlockZ()-i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY(),l.getBlockZ()-i).setType(Material.AIR);
                            l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY()-1,l.getBlockZ()-i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+1,l.getBlockY()-1,l.getBlockZ()-i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+1,l.getBlockY(),l.getBlockZ()-i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+1,l.getBlockY()+1,l.getBlockZ()-i).breakNaturally();
                        }
                        break;
                    case SOUTH:
                        for(int i =0; i < 3; i++)
                        {
                            l.getWorld().getBlockAt(l.getBlockX()-1,l.getBlockY()+1,l.getBlockZ()+i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()-1,l.getBlockY()-1,l.getBlockZ()+i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()-1,l.getBlockY(),l.getBlockZ()+i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY()+1,l.getBlockZ()+i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY(),l.getBlockZ()+i).setType(Material.AIR);
                            l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY()-1,l.getBlockZ()+i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+1,l.getBlockY()-1,l.getBlockZ()+i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+1,l.getBlockY(),l.getBlockZ()+i).breakNaturally();
                            l.getWorld().getBlockAt(l.getBlockX()+1,l.getBlockY()+1,l.getBlockZ()+i).breakNaturally();
                        }
                        break;
                }
            }
        }
    }





    @EventHandler
    public void DeSpawn(EntityInteractEvent e)
    {
        if(e.getEntity().getType() == EntityType.BEE)
        {
            Bukkit.getPlayer("snowy6409").sendMessage("Bee Event:" + e.getEventName());
        }
    }

}
