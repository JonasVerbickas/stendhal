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
import games.stendhal.server.maps.semos.library.HistorianGeographerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class MeetZynnTest {

	private static final String QUEST_SLOT = "meet_zynn";

	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}

	@Before
	public void setUp() {
		final ZoneConfigurator zoneConf = new HistorianGeographerNPC();
		zoneConf.configureZone(new StendhalRPZone("admin_test"), null);
		
		
		npc = SingletonRepository.getNPCList().get("Zynn Iwuhos");
		en = npc.getEngine();

		final AbstractQuest quest = new MeetZynn();
		quest.addToWorld();

		player = PlayerTestHelper.createPlayer("player");
	}

	@Test
	public void testEachQuestionIncreasesXP() {
		// Setup test-specific variables
		int oldXP = player.getXP();
		en.step(player, "hi");
		getReply(npc);
		
		// Test each question asked increases XP by 5 points
		en.step(player, "history");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "news");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "geography");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "places");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "Faiumoni");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "Ados");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "Or'ril");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "Nalwor");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "Deniran");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "use");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "levels");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "naming");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "positioning");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "get");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "SPS");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
		
		en.step(player, "Io");
		assertEquals(oldXP + 5, player.getXP());
		oldXP = player.getXP();
	}
}