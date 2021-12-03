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
package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.SleepStatus;

/**
 * a sleeping bag which can be slept in.
 *
 * @author Dillan
 */
public class SleepingBag extends Box {
	/**
	 * Creates a new sleeping bag.
	 *
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public SleepingBag(final String name, final String clazz, final String subclass,
			final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}

	/**
	 * Copy constructor.
	 *
	 * @param item
	 *            item to copy
	 */
	public SleepingBag(final SleepingBag item) {
		super(item);
	}

	@Override
	protected boolean useMe(final Player player) {
		Item item = SingletonRepository.getEntityManager().getItem("sleeping bag");
		SleepStatus myStatus = new SleepStatus();
		if (!(player.getStatusList().inflictStatus(myStatus, item))){
			return false;
		}
		player.notifyWorldAboutChanges();
		return true;
	}

}
