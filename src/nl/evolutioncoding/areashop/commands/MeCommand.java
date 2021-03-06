package nl.evolutioncoding.areashop.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.evolutioncoding.areashop.AreaShop;
import nl.evolutioncoding.areashop.regions.BuyRegion;
import nl.evolutioncoding.areashop.regions.GeneralRegion;
import nl.evolutioncoding.areashop.regions.RentRegion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand extends CommandAreaShop {

	public MeCommand(AreaShop plugin) {
		super(plugin);
	}
	
	@Override
	public String getCommandStart() {
		return "areashop me";
	}

	@Override
	public String getHelp(CommandSender target) {
		if(target.hasPermission("areashop.me")) {
			return plugin.getLanguageManager().getLang("help-me");
		}
		return null;
	}
	
	@Override
	public void execute(CommandSender sender, Command command, String[] args) {
		if(!sender.hasPermission("areashop.me")) {
			plugin.message(sender, "me-noPermission");
			return;
		}		
		if(!(sender instanceof Player)) {
			plugin.message(sender, "me-notAPlayer");
			return;
		}
		Player player = (Player)sender;
		// Get the regions owned by the player
		Set<RentRegion> rentRegions = new HashSet<RentRegion>();
		for(RentRegion region : plugin.getFileManager().getRents()) {
			if(region.isOwner(player)) {
				rentRegions.add(region);
			}
		}
		Set<BuyRegion> buyRegions = new HashSet<BuyRegion>();
		for(BuyRegion region : plugin.getFileManager().getBuys()) {
			if(region.isOwner(player)) {
				buyRegions.add(region);
			}
		}
		// Send messages
		if(rentRegions.isEmpty()) {
			plugin.message(player, "me-noRentRegions");
		} else {
			plugin.message(player, "me-rentRegions");
			for(RentRegion region : rentRegions) {
				plugin.messageNoPrefix(player, "me-rentLine", region);
			}
		}
		if(buyRegions.isEmpty()) {
			plugin.message(player, "me-noBuyRegions");
		} else {
			plugin.message(player, "me-buyRegions");
			for(BuyRegion region : buyRegions) {
				plugin.messageNoPrefix(player, "me-buyLine", region);
			}
		}
		Set<GeneralRegion> friendRegions = new HashSet<GeneralRegion>();
		for(GeneralRegion region : plugin.getFileManager().getRegions()) {
			if(region.getFriends() != null && region.getFriends().contains(player.getUniqueId())) {
				friendRegions.add(region);
			}
		}
		if(friendRegions.isEmpty()) {
			plugin.message(player, "me-noFriendRegions");
		} else {
			plugin.message(player, "me-friendRegions");
			for(GeneralRegion region : friendRegions) {
				plugin.messageNoPrefix(player, "me-friendLine", region);
			}
		}
		
	}
	
	@Override
	public List<String> getTabCompleteList(int toComplete, String[] start, CommandSender sender) {
		List<String> result = new ArrayList<String>();
		// Nothing to complete
		return result;
	}
}


























