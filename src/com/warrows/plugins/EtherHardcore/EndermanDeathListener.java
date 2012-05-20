package com.warrows.plugins.EtherHardcore;

import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EndermanDeathListener implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onMobDeathEvent(EntityDeathEvent event)
	{
		if (!(event.getEntity() instanceof Enderman))
			return;

		event.getDrops().add(new ItemStack(19, 1));
	}
}