package com.warrows.plugins.EtherHardcore;

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
		if (!(event.getItem().getItemStack().getType() == Material.SPONGE))
			return;

		Player player = event.getPlayer();
		ItemStack sponges = event.getItem().getItemStack();
		int number = sponges.getAmount();

		EtherHardcore.economy.depositPlayer(player.getName(), number);
		if (number == 1)
			player.sendMessage("Vous avez ramassé 1 Hurbron");
		else
			player.sendMessage("Vous avez ramassé " + number + " Hurbrons");
	}
}
