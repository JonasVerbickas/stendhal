package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.maps.deniran.cityinterior.dressshop.OutfitLenderNPC;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class StealingPetQuestTest extends ZonePlayerAndNPCTestImpl {
	private static final String ZONE_NAME = "0_deniran_city_e";

	private SpeakerNPC npc;
	private Engine en;

	private String questSlot;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public StealingPetQuestTest() {
		setNpcNames("StealingPetNPC");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new OutfitLenderNPC(), ZONE_NAME);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		quest = new StealingPetQuest();
		quest.addToWorld();

		questSlot = quest.getSlotName();
	}

	@Test
	public void testLureOutPet() {
		String responseToGreeting = startTalkingToNpc("StealingPetNPC");
		assertEquals(
				"A mysterious animal has stolen all my jewellery. If you manage to find a sapphire we could lure him out.",
				responseToGreeting);
		en.step(player, "bye");
		assertEquals("Well, come back when you do. I'll be waiting for you.", getReply(npc));
		en.step(player, "hi");
		assertEquals("Have you found a sapphire?", getReply(npc));
		en.step(player, "no");
		assertEquals("Well, come back when you do. I'll be waiting for you.", getReply(npc));
		equipWithItem(player, "sapphire");
		en.step(player, "hi");
		assertEquals("Have you found a sapphire?", getReply(npc));
		en.step(player, "done");
		assertEquals("HOW DID YOU TAME IT?!? Have some flowers for your trouble.", getReply(npc));
		assertGainKarma(10);
		assertTrue(player.getQuest(questSlot).startsWith("done"));
		// test history and whether the player received a pet
		// here ^
		en.step(player, "hi");
		assertEquals(
				"How's your new pet holding up?",
				getReply(npc));
	}

	private String startTalkingToNpc(String name) {
		npc = SingletonRepository.getNPCList().get(name);
		en = npc.getEngine();

		en.step(player, "hi");
		return getReply(npc);
	}
}
