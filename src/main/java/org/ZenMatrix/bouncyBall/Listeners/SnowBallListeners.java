package org.ZenMatrix.bouncyBall.Listeners;

import org.ZenMatrix.bouncyBall.BouncyBall;
import org.ZenMatrix.bouncyBall.Commands.BallCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class SnowBallListeners implements Listener {

    private final BouncyBall plugin;

    public SnowBallListeners(BouncyBall plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void OnSnowBallHit(ProjectileHitEvent event) {

        ItemStack bouncyBall = new ItemStack(Material.SNOWBALL);
        ItemMeta bouncyBallMeta = bouncyBall.getItemMeta();
        bouncyBallMeta.setDisplayName("Bouncy Ball");
        bouncyBallMeta.setLore(Arrays.asList("A Ball That Bounce"));
        bouncyBallMeta.addEnchant(Enchantment.SHARPNESS,5,false);
        bouncyBall.setItemMeta(bouncyBallMeta);

        if (event.getEntity() instanceof Snowball snowball) {

            if (snowball.getItem().getItemMeta().hasLore() && snowball.getItem().getItemMeta().getLore().contains("A Ball That Bounce")) {



                if (event.getHitBlock() != null) {

                    // Get the direction of the face that was hit
                    BlockFace blockFace = event.getHitBlockFace();
                    Vector velocity = blockFace.getDirection().multiply(plugin.getConfig().getDouble("MultiplyVelocity"));

                    // Calculate the spawn location for the new snowball, which will be based on the block hit
                    Location spawnLocation = event.getHitBlock().getLocation().add(blockFace.getDirection().add(new Vector(0.5,0.5,0.5)));

                    // Spawn the new snowball at the calculated location
                    Snowball newSnowball = (Snowball) snowball.getWorld().spawnEntity(spawnLocation, EntityType.SNOWBALL);

                    // Set the velocity of the new snowball
                    newSnowball.setVelocity(velocity);
                    newSnowball.setItem(bouncyBall);
                }
            }

        }

    }

    @EventHandler
    public void OnSnowBallLaunched(ProjectileLaunchEvent event) {// Debug line

        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();

            // Check if the custom name is set and not null
            if (snowball.getItem().getItemMeta().hasLore() && snowball.getItem().getItemMeta().getLore().contains("A Ball That Bounce")) {

                // Send message to all online players


                // Get delay from config (ensure the value is correctly set in the config)
                long delay = 20 * plugin.getConfig().getLong("BallActiveTime");

                // Remove the snowball after the delay
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        snowball.remove();

                    }
                }.runTaskLater(plugin, delay);
            }
        }
    }
    /*
    @EventHandler
    public void OnPlayerRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getItem() != null && event.getItem().getType().equals(Material.SNOWBALL)) {
                ItemStack item = event.getItem();
                ItemMeta itemMeta = item.getItemMeta();

                if (itemMeta != null && itemMeta.hasDisplayName()) {
                    String itemDisplayName = itemMeta.getDisplayName();

                    // Create the snowball projectile
                    Player player = event.getPlayer();
                    Snowball snowball = player.launchProjectile(Snowball.class);

                    // Set the custom name of the projectile to match the item's display name
                    snowball.setCustomName(itemDisplayName); // Set the name of the projectile

                    // Optionally set metadata if you want to track it
                    snowball.setMetadata("Bounce", new FixedMetadataValue(plugin, true));

                    // You can also customize the snowball's behavior, such as adding custom lore
                    snowball.setCustomNameVisible(true); // Make the name visible to everyone

                    event.setCancelled(true); // Cancel the event to prevent any additional actions
                }

            }
   }    }
    */

}