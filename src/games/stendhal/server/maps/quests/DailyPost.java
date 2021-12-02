package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.List;

import games.stendhal.server.entity.player.Player;

public class DailyPost extends AbstractQuest {
	@Override
	public String getSlotName() {
		return "dailypost";
	}
	
	@Override
	public void addToWorld() {
		// TODO
	}
	
	@Override
	public String getName() {
		return "DailyPost";
	}
	
	@Override
	public boolean isVisibleOnQuestStatus() {
		return false;
	}

	@Override
	public List<String> getHistory(final Player player) {
		return new ArrayList<String>();
	}

	@Override
	public String getNPCName() {
		return "News Seller";
	}
	
}
