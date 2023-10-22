package tv.tirco.wishingwell;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage("Enabling Halloween Coins.");
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
    	Bukkit.getConsoleSender().sendMessage("Event triggered.");
            Player player = event.getPlayer();
            Item droppedItem = event.getItemDrop();

            if (droppedItem.getItemStack().getType() == Material.GOLD_NUGGET) {
            	Bukkit.getConsoleSender().sendMessage("Nugget dropped!");
                ItemStack itemStack = droppedItem.getItemStack();
                ItemMeta meta = itemStack.getItemMeta();

                if (meta != null && meta.hasCustomModelData() && meta.getCustomModelData() == 1337) {
                	Bukkit.getConsoleSender().sendMessage("Meta correct!.");
                	droppedItem.setPickupDelay(20*10);
                	Bukkit.getServer().getScheduler().runTaskLater(this, () -> {
                		Bukkit.getConsoleSender().sendMessage("Running task!");
                        if (droppedItem.getLocation().getBlock().getType().equals(Material.WATER)) {
                            applyEffects(player, itemStack.getAmount());
                            droppedItem.remove();
                        }
                    }, 60L);
                }
        }
    }

    private void applyEffects(Player player, int coinCount) {
        if (coinCount >= 10) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 2)); // 30 seconds of Regeneration III
            player.sendMessage("You received a strong blessing for 10 or more coins!");
        } else if (coinCount >= 5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 1)); // 20 seconds of Regeneration II
            player.sendMessage("You received a blessing for 5 or more coins!");
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 0)); // 10 seconds of Regeneration I
            player.sendMessage("You received a minor blessing!");
        }
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent event) {
        ItemStack sourceStack = event.getEntity().getItemStack();
        ItemStack targetStack = event.getTarget().getItemStack();

        if (sourceStack.getType() == Material.GOLD_NUGGET && targetStack.getType() == Material.GOLD_NUGGET) {
            ItemMeta sourceMeta = sourceStack.getItemMeta();

            if (sourceMeta != null && sourceMeta.hasCustomModelData() && sourceMeta.getCustomModelData() == 1337) {
                    event.setCancelled(true);
            }
        }
    }
}
