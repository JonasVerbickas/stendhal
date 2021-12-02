package games.stendhal.server.core.rp.achievement;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

import static games.stendhal.server.core.rp.achievement.factory.FightingAchievementFactory.ENEMIES_BEARS;
import static games.stendhal.server.core.rp.achievement.factory.FightingAchievementFactory.ID_BEARS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.server.game.db.DatabaseFactory;
import utilities.AchievementTestHelper;
import utilities.PlayerTestHelper;
import utilities.ZoneAndPlayerTestImpl;


public class AchievementNotifierTest extends ZoneAndPlayerTestImpl {

	private final int count = 10;

	@BeforeClass
	public static void setUpBeforeClass() {
		new DatabaseFactory().initializeDatabase();
		// initialize world
		MockStendlRPWorld.get();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		zone = setupZone("testzone");
		AchievementTestHelper.setEnemyNames(ENEMIES_BEARS);
	}

	@Test
	public void init() {
		final int requiredKills = count * ENEMIES_BEARS.length;
		
		Field[] allFields = SingletonRepository.class.getFields();
		
		assertTrue(Arrays.stream(allFields).anyMatch(field ->
	        field.getName().equals("allAchievements") && field.getType().equals(ArrayList.class))
	    );
		assertTrue(Arrays.stream(allFields).anyMatch(field ->
	        field.getName().equals("allAchievedBy") && field.getType().equals(ArrayList.class))
	    );
		
		Field allAchievements = null, allAchievedBy = null;
		try {
			allAchievements = SingletonRepository.class.getField("allAchievements");
			allAchievedBy = SingletonRepository.class.getField("allAchievedBy");
			
		} catch (Exception e) {
			assertTrue(false);
		}

		// solo kills
		int totalKills = 0;
		resetPlayer();
		for (final String enemy: ENEMIES_BEARS) {
			int killCount = player.getSoloKill(enemy);
			assertEquals(0, killCount);

			while (killCount < count) {
				killCount++;
				player.setSoloKillCount(enemy, killCount);
				AchievementNotifier.get().onKill(player);
			}

			totalKills += killCount;

			if (totalKills < requiredKills) {
				assertFalse(achievementReached());
			}
		}
		assertTrue(achievementReached());
		
		try {
			ArrayList<String> allAchievementsValues = (ArrayList<String>) allAchievements.get(null);
			ArrayList<String> allAchievedByValues = (ArrayList<String>) allAchievedBy.get(null);
			assertFalse(allAchievementsValues.isEmpty());
			assertFalse(allAchievedByValues.isEmpty());
			assertEquals(allAchievementsValues.get(allAchievementsValues.size() - 1), "Bear Hunter");
			assertEquals(allAchievedByValues.get(allAchievedByValues.size() - 1), player.getName());
			
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	private void resetPlayer() {
		if (player != null) {
			PlayerTestHelper.removePlayer(player.getName(), "testzone");
		}
		player = PlayerTestHelper.createPlayer("player");
		player.setPosition(0, 0);
		zone.add(player);
		assertNotNull(player);

		for (final String enemy: ENEMIES_BEARS) {
			assertFalse(player.hasKilled(enemy));
		}

		AchievementTestHelper.init(player);
		assertFalse(achievementReached());
	}

	private boolean achievementReached() {
		return AchievementTestHelper.achievementReached(player, ID_BEARS);
	}
}
