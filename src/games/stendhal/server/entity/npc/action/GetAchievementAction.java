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
package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.annotations.Dev;
import games.stendhal.server.core.config.annotations.Dev.Category;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.player.PlayerMapAdapter;
import games.stendhal.server.util.StringUtils;

import games.stendhal.server.core.engine.SingletonRepository;



/**
 * says the most recent achievement
 *
 * But in addition it add support for [variables]. Most notable [name] will
 * be replaced by the players name. And [quest.slotname:1] will be replaced
 * by the value stored in the questslot "slotname" at index 1.
 */
@Dev(category=Category.CHAT, label="\"...\"")
public class GetAchievementAction implements ChatAction {

	private final String text;
	private boolean presentType;

	/**
	 * Creates a new GetAchievementAction.
	 *
	 * @param text text to say
	 */
	public GetAchievementAction(boolean presentType) {
		this.text = "GetAchievementAction";
		this.presentType = presentType;
	}

	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
		PlayerMapAdapter map = new PlayerMapAdapter(player);
				
		String mainText = "";
		if(SingletonRepository.allAchievements.size() == 0) {
			mainText = "No achievements yet";
		}
		else {
			mainText = SingletonRepository.allAchievements.get(0) + " by " + SingletonRepository.allAchievedBy.get(0);
		}
		
		String secondaryText = "";
		if(SingletonRepository.allAchievements.size() <= 1) {
			secondaryText = "No secondary achievements yet";
		}
		else {
			secondaryText = SingletonRepository.allAchievements.get(1) + " by " + SingletonRepository.allAchievedBy.get(1);
		
			for(int i=2;i<Math.min(4, SingletonRepository.allAchievements.size());i++) {
				secondaryText += ", " + SingletonRepository.allAchievements.get(i) + " by " + SingletonRepository.allAchievedBy.get(i);
			}
		}
		
		if(presentType)
			raiser.say(StringUtils.substitute(mainText, map));
		else 
			raiser.say(StringUtils.substitute(secondaryText, map));
	}

	@Override
	public String toString() {
		return "GetAchievement";
	}

	@Override
	public int hashCode() {
		return 5417 * text.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof GetAchievementAction)) {
			return false;
		}
		GetAchievementAction other = (GetAchievementAction) obj;
		return text.equals(other.text);
	}
}
