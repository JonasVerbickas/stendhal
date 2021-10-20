/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
// import static org.junit.Assert.assertFalse;
// import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.portal.Door;
import games.stendhal.server.entity.mapstuff.sign.Sign;
import games.stendhal.server.entity.npc.NPCList;
// import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPClass;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class ReverseArrowTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		QuestHelper.setUpBeforeClass();
		StendhalRPZone zone = new StendhalRPZone("int_ados_reverse_arrow");
		MockStendlRPWorld.get().addRPZone(zone);
		MockStendlRPWorld.get().addRPZone(new StendhalRPZone("0_semos_mountain_n2"));

		if (!RPClass.hasRPClass("door")) {
			Door.generateRPClass();
		}
		if (!RPClass.hasRPClass("sign")) {
			Sign.generateRPClass();
		}

	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		MockStendlRPWorld.reset();
		NPCList.get().clear();
	}


	/**
	 * Tests for getSlotName.
	 */
	@Test
	public void testGetSlotName() {
		assertEquals("reverse_arrow", new ReverseArrow().getSlotName());
	}

	/**
	 * Tests for addToWorld.
	 */
	@Test
	public void testAddToWorld() {

		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
	}

	/**
	 * Tests for finish.
	 */
	@Test
	public void testFinish() {
		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(arrowquest.player);
		arrowquest.finish(false, null);
		assertNotNull(arrowquest.player);

		arrowquest.finish(true, null);
		assertNull(arrowquest.player);
	}

	/**
	 * Tests for validity of arrow shape
	 */
	// @Test
	// public void testCorrectPattern1() {
	// 	ReverseArrow arrowquest = new ReverseArrow();
	// 	arrowquest.addToWorld();
	// 	Player bob = PlayerTestHelper.createPlayer("bob");
	// 	PlayerTestHelper.registerPlayer(bob, SingletonRepository.getRPWorld().getZone("int_ados_reverse_arrow"));

	// 	arrowquest.start(bob);

	// 	assertTrue(arrowquest.player.isQuestCompleted("reverse_arrow"));
	// }

	@Test
	public void testWrongPattern(){
		// 0 1 2 3 4
		// * 5 6 7 *
		// * * 8 * *   
		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();

		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		arrowquest.zone.add(arrowquest.player);
		assertEquals(arrowquest.zone, arrowquest.player.getZone());

		arrowquest.start(arrowquest.player);
		assertEquals(null, arrowquest.player.getQuest(arrowquest.getName()));
		
		arrowquest.new ReverseArrowCheck().onTurnReached(0);
		assertEquals("failed", arrowquest.player.getQuest(arrowquest.getSlotName()));
	}
	
	@Test
	public void testCorrectPattern1() {
		System.out.println("Started test correct pattern");
		// * * 8 * *
		// * 1 2 3 *
		// 0 5 6 7 4     
		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();

		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		arrowquest.zone.add(arrowquest.player);
		arrowquest.start(arrowquest.player);

		arrowquest.tokens.get(8).setPosition(arrowquest.tokens.get(8).getX(), arrowquest.tokens.get(8).getY()-3);
		arrowquest.tokens.get(0).setPosition(arrowquest.tokens.get(0).getX(), arrowquest.tokens.get(0).getY()+1);
		arrowquest.tokens.get(4).setPosition(arrowquest.tokens.get(4).getX(), arrowquest.tokens.get(4).getY()+1);
		
		arrowquest.new ReverseArrowCheck().onTurnReached(0);
		assertEquals("done", arrowquest.player.getQuest(arrowquest.getSlotName()));
	}
	
	@Test
	public void testCorrectPattern2() {
		System.out.println("Started test correct pattern");
		// * * 2 * *
		// * 1 * 3 *
		// 0 5 6 7 4
		// * * 8 * *
		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();

		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		arrowquest.zone.add(arrowquest.player);
		arrowquest.start(arrowquest.player);

		arrowquest.tokens.get(2).setPosition(arrowquest.tokens.get(2).getX(), arrowquest.tokens.get(2).getY()-1);
		arrowquest.tokens.get(0).setPosition(arrowquest.tokens.get(0).getX(), arrowquest.tokens.get(0).getY()+1);
		arrowquest.tokens.get(4).setPosition(arrowquest.tokens.get(4).getX(), arrowquest.tokens.get(4).getY()+1);
		
		arrowquest.new ReverseArrowCheck().onTurnReached(0);
		assertEquals("done", arrowquest.player.getQuest(arrowquest.getSlotName()));
	}
}
