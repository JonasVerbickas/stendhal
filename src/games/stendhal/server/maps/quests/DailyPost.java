package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.List;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.npc.action.GetAchievementAction;

public class DailyPost extends AbstractQuest {
	@Override
	public String getSlotName() {
		return "dailypost";
	}
	
	private void step_1() {
		final SpeakerNPC npc = npcs.get("News Seller");
		
		npc.addReply("main", "The main achievement now is:", new GetAchievementAction(true));
		npc.addReply("secondary", "The secondary achievements currently are", new GetAchievementAction(false));
	}
	
	@Override
	public void addToWorld() {
		fillQuestInfo(
				"DailyPost",
				"The daily news seller is the best source of new information",
				false
		);
		step_1();
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