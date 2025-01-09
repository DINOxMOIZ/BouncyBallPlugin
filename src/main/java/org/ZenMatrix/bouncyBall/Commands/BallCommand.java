package org.ZenMatrix.bouncyBall.Commands;

import org.ZenMatrix.bouncyBall.BouncyBall;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class BallCommand implements CommandExecutor, TabExecutor {




    private final BouncyBall plugin;

    public BallCommand(BouncyBall plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player){
            ItemStack bouncyBall = new ItemStack(Material.SNOWBALL);
            ItemMeta bouncyBallMeta = bouncyBall.getItemMeta();
            bouncyBallMeta.setDisplayName("Bouncy Ball");
            bouncyBallMeta.setLore(Arrays.asList("A Ball That Bounce"));
            bouncyBallMeta.addEnchant(Enchantment.SHARPNESS,5,false);
            bouncyBall.setItemMeta(bouncyBallMeta);

            player.getInventory().addItem(bouncyBall);

        }else {
            commandSender.sendMessage("Only Players Can Use This Command");
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of();
    }
}
