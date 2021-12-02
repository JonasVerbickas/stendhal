package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.StatusType;
import utilities.PlayerTestHelper;

public class SleepingBagTest {

	/**
	 * Tests if player can sleep in sleeping bag
	 */
	@Test
	public void testSleepingBag() {
		final SleepingBag sleepingBag = (SleepingBag) SingletonRepository.getEntityManager().getItem("sleeping bag");
		final Player player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(player);
		final StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);

		assertNotNull(sleepingBag);
		player.equip("bag", sleepingBag);

		assertTrue(sleepingBag.useMe(player));
	}
	
	/**
	 * Tests if changing direction stops you from sleeping
	 */
	@Test
	public void testWalkingStopsSleeping() {
		//Create a sleeping bag object called sleepingBag
		final SleepingBag sleepingBag = (SleepingBag) SingletonRepository.getEntityManager().getItem("sleeping bag");
		final Player player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(sleepingBag);
		player.equip("bag", sleepingBag);
		player.setPosition(0, 0);
		assertTrue(sleepingBag.useMe(player));
		
		assertTrue(player.hasStatus(StatusType.SLEEPING));
		player.addClientDirection(player.getDirection().oppositeDirection());
		player.applyClientDirection(true);
		assertFalse(player.hasStatus(StatusType.SLEEPING));
	}
	
	
}
