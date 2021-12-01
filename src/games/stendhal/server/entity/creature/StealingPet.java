/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.creature;

import games.stendhal.server.entity.player.Player;
import java.util.List;

public class StealingPet extends Pet {

	public StealingPet(final Player owner) {
		super();
	}

	
	@Override
	protected List<String> getFoodNames() {
		return null;
	}

	@Override
	void setUp() {
	}
}
