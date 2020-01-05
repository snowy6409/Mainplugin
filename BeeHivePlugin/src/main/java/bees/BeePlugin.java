package bees;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class BeePlugin extends JavaPlugin {
    @Override
    public void onEnable()
    {
        this.getCommand("Bee").setExecutor(new GiveCustomBee());
        this.saveDefaultConfig();
        ItemStack is = new ItemStack(Material.TRIPWIRE_HOOK);
        NamespacedKey key = new NamespacedKey(this, "tripwire_hook");
        ShapedRecipe recipe = new ShapedRecipe(key, is);

        recipe.shape("ICI", "III", "ISI");
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('C', Material.CONDUIT);
        recipe.setIngredient('S',Material.PLAYER_HEAD);
        Bukkit.addRecipe(recipe);

        this.getConfig().set("player-name","FICK");
        getServer().getPluginManager().registerEvents(new Events(this),this);
        this.getCommand("Loc").setExecutor(new GetLocation());
        this.getCommand("Test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable()
    {

    }
}
