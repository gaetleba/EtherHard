package com.warrows.plugins.EtherHardcore;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class EpongesListener implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onPlayerPickupEvent(PlayerPickupItemEvent event)
	{
		EtherHardcore.log.info("EtherHard: A");
		if ((event.getItem().getItemStack().getType() != Material.SPONGE))
		{
			EtherHardcore.log.info("EtherHard: B");
			return;
		}

		Player player = event.getPlayer();
		ItemStack sponges = event.getItem().getItemStack();
		double number = (double) sponges.getAmount();
		EtherHardcore.log.info("EtherHard: " + number);
		EtherHardcore.economy.depositPlayer(player.getName(), number);
		if (number == 1)
			player.sendMessage("Vous avez ramassé 1 Hurbron");
		else
			player.sendMessage("Vous avez ramassé " + number + " Hurbrons");
		event.getItem().teleport(new Location(EtherHardcore.world, -10, -10, -10));
		event.setCancelled(true);
	}
}
