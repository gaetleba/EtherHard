package com.warrows.plugins.EtherHardcore;

import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class EpongesListener implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onPlayerEvent(PlayerPickupItemEvent event)
	{
		if (! (event.getItem() instanceof Block))
			return;
	
		event.getDrops().add(new ItemStack(19,1));
	}
}
