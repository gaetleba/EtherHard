package com.warrows.plugins.EtherHardcore;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player mort = event.getEntity();
		Player tueur = null;

		if ((mort.getLastDamageCause() instanceof Player))
		{
			tueur = ((Player) mort).getKiller();
		} else
		{
			if ((mort.getLastDamageCause() instanceof Projectile))
			{
				if (((Projectile) mort).getShooter() instanceof Player)
				{
					tueur = (Player) ((Projectile) mort).getShooter();
				}
			}
		}

		/**
		 * si la cause de la mort n'est pas un joueur, on se moque du joueur,
		 * lui enleve un hurbron et on sors de la fonction
		 */
		if (tueur == null)
		{
			killPlayer(
					mort,
					"Vous �tes mort comme un faible, un Hurbron vous a quitt�.",
					1.0);
			return;
		}

		/**
		 * Cet int repr�sente le diff�rentiel de hurbron entre l'assassin et sa
		 * victime. Si il est positif, l'assassin est plus fort. Si il est
		 * n�gatif, la proie est plus forte.
		 */
		int diff = (int) EtherHardcore.economy.getBalance(tueur.getName())
				- (int) EtherHardcore.economy.getBalance(mort.getName());

		/**
		 * en cas de kill � niveaux �quivalents, -1/+1
		 */
		if (diff < -10)
		{
			killPlayer(mort, "Vous avez �t� tu�. Un Hurbron vous a quitt�.",
					1.0);
			EtherHardcore.economy.depositPlayer(tueur.getName(), 1.0);
			tueur.sendMessage("Vous avez vaincu votre adversaire. Un de ses hurbrons vous � rejoint.");
			// TODO algo de r�cup �quilibr�
		} else if (diff < 5)
		{
			killPlayer(mort, "Vous avez �t� tu�. Un Hurbron vous a quitt�.",
					1.0);
			EtherHardcore.economy.depositPlayer(tueur.getName(), 1.0);
			tueur.sendMessage("Vous avez vaincu votre adversaire. Un de ses hurbrons vous � rejoint.");
		} else if (diff < 10)
		{
			killPlayer(
					mort,
					"Vous avez �t� tu� par plus fort que vous, un Hurbron vous a quitt�.",
					1.0);
			tueur.sendMessage("Vous avez vaincu plus faible que vous. Pour rien.");
		} else
		{
			killPlayer(tueur,
					"Vous avez tu� un faible, un Hurbron vous a quitt�.", 1.0);
			mort.sendMessage("Votre tueur �tait un lache. Un de ses hurbrons l'a quitt� pour sauver votre vie.");
		}
	}

	private void killPlayer(Player mort, String msg, double penalty)
	{
		EtherHardcore.economy.withdrawPlayer(mort.getName(), penalty);
		Antilogin.addDeadPlayer(mort);
		mort.getInventory().clear();
		ItemStack[] noArmor = new ItemStack[4];
		noArmor[0] = new ItemStack(0);
		noArmor[1] = new ItemStack(0);
		noArmor[2] = new ItemStack(0);
		noArmor[3] = new ItemStack(0);
		mort.getInventory().setArmorContents(noArmor);
		mort.kickPlayer(msg);
	}
}
