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

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.game.SyntaxException;

public class StealingPet extends Pet {

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(StealingPet.class);

	@Override
	void setUp() {
		HP = 100;
		incHP = 4;
		ATK = 10;
		DEF = 50;
		XP = 100;
		baseSpeed = 0.9;

		setAtk(ATK);
		setDef(DEF);
		setXP(XP);
		setBaseHP(HP);
		setHP(HP);
		// from 20 to 50 - see more
		setPerceptionRange(50);
	}

	public static void generateRPClass() {
		try {
			final RPClass stealingPet = new RPClass("stealing_pet");
			stealingPet.isA("pet");
		} catch (final SyntaxException e) {
			logger.error("cannot generate RPClass", e);
		}
	}

	/**
	 * Creates a new wild Cat.
	 */
	public StealingPet() {
		this(null);
	}

	/**
	 * Creates a new StealingPet that may be owned by a player.
	 * @param owner owning player, or <code>null</code>
	 */
	public StealingPet(final Player owner) {
		// call set up before parent constructor is called as it needs those
		// values
		super();
		setOwner(owner);
		setUp();
		setRPClass("stealing_pet");
		put("type", "stealing_pet");

		if (owner != null) {
			// add pet to zone and create RPObject.ID to be used in setPet()
			owner.getZone().add(this);
			owner.setPet(this);
		}

		update();
	}

	/**
	 * Creates a StealingPet based on an existing monkey RPObject, and assigns it to a
	 * player.
	 *
	 * @param object object containing the data for the Cat
	 * @param owner
	 *            The player who should own the cat
	 */
	public StealingPet(final RPObject object, final Player owner) {
		super(object, owner);
		setRPClass("stealing_pet");
		put("type", "stealing_pet");
		update();
	}

	@Override
	protected List<String> getFoodNames() {
		return Arrays.asList(
				"pinto beans",
				"roach", 
				"egg"
				);
	}

	/**
	 * Does this domestic animal take part in combat?
	 *
	 * @return true, if it can be attacked by creatures, false otherwise
	 */
	@Override
	protected boolean takesPartInCombat() {
		return false;
	}

}
