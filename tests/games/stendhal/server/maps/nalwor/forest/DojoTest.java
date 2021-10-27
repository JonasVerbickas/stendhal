/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.nalwor.forest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class DojoTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "dojo";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public DojoTest() {
		setNpcNames("Omura Sumitada");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new Dojo(), ZONE_NAME);
	}

	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testFeeWhenPlayerIsTooStrong() {
		final SpeakerNPC npc = getNPC("Omura Sumitada");
		final Engine en = npc.getEngine();

		equipWithItem(player, "assassins id");

		// the players attack is low enough to train
		player.setXP(1000);
		player.setLevel(1000);
		player.put("atk", 1);
		player.put("atx_xp", 1);
		assertTrue(en.step(player, "hello"));
		getReply(npc);
		assertTrue(en.step(player, "fee"));
		assertThat(getReply(npc), containsString("The fee to #train for your skill level is "));
		assertTrue(en.step(player, "bye"));

		// the players attack is too high to train
		player.setXP(1);
		player.setLevel(1);
		player.put("atk", 1000);
		player.put("atx_xp", 1000);
		assertTrue(en.step(player, "hello"));
		getReply(npc);
		assertTrue(en.step(player, "fee"));
		assertEquals("At your level of experience, your attack strength is too high to train here at this time.",
				getReply(npc));
		assertTrue(en.step(player, "bye"));

	}

}
