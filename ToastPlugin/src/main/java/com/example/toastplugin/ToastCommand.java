package com.example.toastplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.advancement.AdvancementFrameType;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.UUID;

public class ToastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length < 3) {
            player.sendMessage("Usage: /toast <frame> <material> <text>");
            return true;
        }

        String frameArg = args[0].toLowerCase();
        String materialArg = args[1].toUpperCase();
        String textArg = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));

        AdvancementFrameType frame;
        try {
            frame = AdvancementFrameType.valueOf(frameArg.toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage("Invalid frame type. Use task, goal, or challenge.");
            return true;
        }

        Material material = Material.matchMaterial(materialArg);
        if (material == null) {
            player.sendMessage("Invalid material: " + materialArg);
            return true;
        }

        ItemStack icon = new ItemStack(material);
        Component title = Component.text(textArg);
        Component description = Component.empty();

        AdvancementDisplay display = AdvancementDisplay.builder()
                .icon(icon)
                .title(title)
                .description(description)
                .frame(frame)
                .announceChat(false)
                .toast(true)
                .background(NamespacedKey.minecraft("textures/gui/advancements/backgrounds/adventure.png"))
                .build();

        player.sendAdvancement(
                UUID.randomUUID(),
                display,
                EnumSet.of(AdvancementProgress.CriterionType.IMPOSSIBLE)
        );

        return true;
    }
}
