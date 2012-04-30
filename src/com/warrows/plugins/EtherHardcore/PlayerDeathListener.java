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
			tuerJoueur(mort,"Vous �tes mort comme un faible, un Hurbron vous a quitt�.",1.0);
			return;
		}

		int hurbsTueur = (int) EtherHardcore.economy
				.getBalance(tueur.getName());
		int hurbsMort = (int) EtherHardcore.economy.getBalance(mort.getName());
		
		/**
		 * en cas de kill � niveaux �quivalents, -1/+1
		 */
		if (hurbsMort < (hurbsTueur+10) && hurbsMort > (hurbsTueur-10))
		{
			tuerJoueur(mort,"Un etheranien vous a tu�, un de vos Hurbron l'a rejoint.",1.0);

			EtherHardcore.economy.depositPlayer(tueur.getName(), 1.0);
			tueur.sendMessage("Un Hurbron a quitt� votre victime pour s'ins�rer en vous.");
			return;
		}
		
		/**
		 * si le mort est plus faible que le tueur.
		 */
		if (hurbsMort < (hurbsTueur-10))
		{
			tuerJoueur(mort,"Un etheranien vous a tu�, un de vos Hurbron l'a rejoint.",1.0);
			
			EtherHardcore.economy.depositPlayer(tueur.getName(), 1.0);
			tueur.sendMessage("Vous avez tu� un faible, un Hurbron vous a quitt�.");
			return;
		}
	}
	
	private void tuerJoueur(Player mort, String msg, double penalty)
	{
		EtherHardcore.economy.withdrawPlayer(mort.getName(), penalty);
		Antilogin.addDeadPlayer(mort);
		mort.getInventory().clear();
		ItemStack[] noArmor = new ItemStack[4];
		noArmor[0]= new ItemStack(0);
		noArmor[1]= new ItemStack(0);
		noArmor[2]= new ItemStack(0);
		noArmor[3]= new ItemStack(0);
		mort.getInventory().setArmorContents(noArmor);
		mort.kickPlayer(msg);
	}
}
