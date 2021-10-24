/* $Id$ */
package games.stendhal.server.entity.item.scroll;

import org.junit.Test;

import static games.stendhal.common.constants.Actions.BASEITEM;
import static games.stendhal.common.constants.Actions.BASEOBJECT;
import static games.stendhal.common.constants.Actions.BASESLOT;

import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import games.stendhal.server.actions.UseAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.common.game.RPAction;
import utilities.PlayerTestHelper;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.PoisonStatus;

public class ScrollTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		Log4J.init();
	}

	/**
	 * Tests for if you can use a marked scroll when not poisoned.
	 */
	@Test
	public void testCanTeleportWhenNotPoisoned() {
		// set up the 2 zones needed
		final StendhalRPZone semos_city = new StendhalRPZone("0_semos_city", 50, 50);
		MockStendlRPWorld.get().addRPZone(semos_city);
		final StendhalRPZone fado_city = new StendhalRPZone("0_fado_city", 50, 50);
		MockStendlRPWorld.get().addRPZone(fado_city);
		// home scroll takes you to 0_semos_city
		Item markedScroll = SingletonRepository.getEntityManager().getItem("fado city scroll");
		// create player to use said scroll
		final Player player = PlayerTestHelper.createPlayer("bob");
		// equip the scroll
		player.equip("bag", markedScroll);
		// add the player to semos_city
		semos_city.add(player);
		player.setKeyedSlot("!visited", "0_fado_city", "false");
		RPAction action = new RPAction();
		action.put(BASEITEM, markedScroll.getID().getObjectID());
		action.put(BASEOBJECT, player.getID().getObjectID());
		action.put(BASESLOT, "bag");
		StendhalRPZone playerZone = player.getZone();
		final UseAction ua = new UseAction();
		ua.onAction(player, action);
		assertNotEquals(player.getZone(), playerZone);
	}
	
	/**
	 * Tests for if you can use a marked scroll when poisoned.
	 */
	@Test
	public void testCantTeleportWhenPoisoned() {
		// set up the 2 zones needed
		final StendhalRPZone semos_city = new StendhalRPZone("0_semos_city", 50, 50);
		MockStendlRPWorld.get().addRPZone(semos_city);
		final StendhalRPZone fado_city = new StendhalRPZone("0_fado_city", 50, 50);
		MockStendlRPWorld.get().addRPZone(fado_city);
		// home scroll takes you to 0_semos_city
		Item markedScroll = SingletonRepository.getEntityManager().getItem("fado city scroll");
		// create player to use said scroll
		final Player player = PlayerTestHelper.createPlayer("bob");
		// equip the scroll
		player.equip("bag", markedScroll);
		// add the player to semos_city
		semos_city.add(player);
		player.setKeyedSlot("!visited", "0_fado_city", "false");
		RPAction action = new RPAction();
		action.put(BASEITEM, markedScroll.getID().getObjectID());
		action.put(BASEOBJECT, player.getID().getObjectID());
		action.put(BASESLOT, "bag");
		StendhalRPZone playerZone = player.getZone();
		// poison playere before teleporting
		PoisonStatus status = new PoisonStatus(5, 2, 2);
		player.getStatusList().inflictStatus(status, player);
		// teleport
		final UseAction ua = new UseAction();
		ua.onAction(player, action);
		assertEquals(player.getZone(), playerZone);
	}
}
