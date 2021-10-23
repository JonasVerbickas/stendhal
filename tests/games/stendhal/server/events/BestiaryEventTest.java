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
package games.stendhal.server.events;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;

public class BestiaryEventTest {

	/**
	 * Tests for healedEvent.
	 */
    @Test
    public void testBestiaryEvent() {
    	
    	final Player pl = PlayerTestHelper.createPlayer("player");
    	pl.setSoloKill("rat");
    	assertTrue(pl.hasKilledSolo("rat"));
		BestiaryEvent be = new BestiaryEvent(pl);
		final List<String> enemies = Arrays.asList(be.get("enemies").split(";"));
		for (final String e: enemies) {
			System.out.println(e.toString());
			assertFalse(e.split(",")[0].contains("???"));
		}
    }
}
