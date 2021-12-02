package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.semos.city.DailyPostNPC;
import games.stendhal.server.maps.quests.DailyPost;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class DailyPostTest {
	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        QuestHelper.setUpBeforeClass();
    }
    
    @Before
    public void setUp() {
        player = PlayerTestHelper.createPlayer("player");

    	final ZoneConfigurator zc = new DailyPostNPC();
        zc.configureZone(new StendhalRPZone("admin_test"), null);

        npc = SingletonRepository.getNPCList().get("News Seller");
        en = npc.getEngine();
        
        // set up quest
        final AbstractQuest quest = new DailyPost();
        quest.addToWorld();
    }

    @Test
    public void testNoAchievements() {
    	// reset lists for SingletonRepository
    	SingletonRepository.getNPCList().clear();
    	SingletonRepository.allAchievements.clear();
    	SingletonRepository.allAchievedBy.clear();
    	
        // leave achievement list empty

        // check that npc sees no achievements
        en.step(player, "hi");

        en.step(player, "main");
        String ans = getReply(npc);
        assertEquals("No achievements yet", ans);
        
        en.step(player, "secondary");
        ans = getReply(npc);
        assertEquals("No secondary achievements yet", ans);
    }

    @Test
    public void testMainAchievement() {
    	// reset lists for SingletonRepository
    	SingletonRepository.getNPCList().clear();
    	SingletonRepository.allAchievements.clear();
    	SingletonRepository.allAchievedBy.clear();
    	
        // add one achievement
        SingletonRepository.allAchievements.add("achievement 1");
        SingletonRepository.allAchievedBy.add("player 1");

        // check that npc sees one achievement
        en.step(player, "hi");

        en.step(player, "main");
        String ans = getReply(npc);
        assertEquals("achievement 1 by player 1", ans);
        
        en.step(player, "secondary");
        ans = getReply(npc);
        assertEquals("No secondary achievements yet", ans);
    }

    @Test
    public void testSecondaryAchievement() {
    	// reset lists for SingletonRepository
    	SingletonRepository.getNPCList().clear();
    	SingletonRepository.allAchievements.clear();
    	SingletonRepository.allAchievedBy.clear();
    	
        // add two achievements
        SingletonRepository.allAchievements.add("achievement 1");
        SingletonRepository.allAchievedBy.add("player 1");

        SingletonRepository.allAchievements.add("achievement 2");
        SingletonRepository.allAchievedBy.add("player 2");

        // check that npc sees two achievements
        en.step(player, "hi");

        en.step(player, "main");
        String ans = getReply(npc);
        assertEquals("achievement 1 by player 1", ans);
        
        en.step(player, "secondary");
        ans = getReply(npc);
        assertEquals("achievement 2 by player 2", ans);
    }
}