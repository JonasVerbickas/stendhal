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

import java.util.ArrayList;
import java.util.List;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;

public class StealingPetQuest extends AbstractQuest {

	// constants
	private static final String QUEST_SLOT = "stealing_pet";
	private static final String NPC_NAME = "StealingPetNPC";

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	@Override
	public void addToWorld() {
		fillQuestInfo(
				"Stealing pet thingy",
				"Give sapphire to NPC for a reward",
				true);
	}

	@Override
	public List<String> getHistory(final Player player) {
		final List<String> res = new ArrayList<String>();
		res.add("Not sure what's the point of this method");
		return res;
	}

	@Override
	public String getName() {
		return QUEST_SLOT;
	}

	@Override
	public int getMinLevel() {
		return 0;
	}

	@Override
	public boolean isRepeatable(final Player player) {
		return true;
	}

	@Override
	public boolean isCompleted(final Player player) {
		return false;
	}

	@Override
	public String getRegion() {
		return Region.DENIRAN;
	}

	@Override
	public String getNPCName() {
		return NPC_NAME;
	}
}

