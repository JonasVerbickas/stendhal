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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.common.Rand;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.DropItemAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;

import games.stendhal.server.entity.creature.StealingPet;

public class StealingPetQuest extends AbstractQuest {

	// constants
	private static final String QUEST_SLOT = "stealing_pet";
	private static final String NPC_NAME = "StealingPetNPC";

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	private void firstStep() {
		final SpeakerNPC npc = npcs.get(NPC_NAME);

		final List<ChatAction> reward = new LinkedList<ChatAction>();
		reward.add(new DropItemAction("sapphire"));
		reward.add(new ChatAction() {
			@Override
			public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
				// pick a random flower
				String rewardClass = Rand.rand(Arrays.asList("daisies", "zantedeschia", "pansy"));

				final StackableItem item = (StackableItem) SingletonRepository.getEntityManager().getItem(rewardClass);
				item.setQuantity(1);
				player.equipOrPutOnGround(item);
				StealingPet stealing_pet = new StealingPet(player);
				stealing_pet.setPosition(player.getX(), player.getY() + 1);
				player.notifyWorldAboutChanges();
			}
		});
		reward.add(new IncreaseXPAction(500));
		reward.add(new SetQuestAction(QUEST_SLOT, "done"));
		reward.add(new IncreaseKarmaAction(10.0));

		npc.add(ConversationStates.IDLE,
				ConversationPhrases.GREETING_MESSAGES,
				new QuestNotStartedCondition(QUEST_SLOT),
				ConversationStates.ATTENDING,
				"A mysterious animal has stolen all my jewellery. If you manage to find a sapphire we could lure him out.",
				new SetQuestAction(QUEST_SLOT, "start"));

		npc.add(ConversationStates.ATTENDING,
				ConversationPhrases.YES_MESSAGES,
				new AndCondition(new PlayerHasItemWithHimCondition("sapphire"),
						new QuestInStateCondition(QUEST_SLOT, "start")),
				ConversationStates.IDLE,
				"HOW DID YOU TAME IT?!? Have some flowers for your trouble.",
				new MultipleActions(reward));

		npc.add(ConversationStates.ATTENDING,
				ConversationPhrases.FINISH_MESSAGES,
				new AndCondition(new PlayerHasItemWithHimCondition("sapphire"),
						new QuestInStateCondition(QUEST_SLOT, "start")),
				ConversationStates.IDLE,
				"HOW DID YOU TAME IT?!? Have some flowers for your trouble.",
				new MultipleActions(reward));

		npc.add(ConversationStates.ATTENDING,
				ConversationPhrases.NO_MESSAGES,
				new QuestInStateCondition(QUEST_SLOT,
						"start"),
				ConversationStates.IDLE,
				"Well, come back when you do. I'll be waiting for you.",
				null);

		npc.add(ConversationStates.ATTENDING,
				ConversationPhrases.GOODBYE_MESSAGES,
				new QuestInStateCondition(QUEST_SLOT, "start"),
				ConversationStates.IDLE,
				"Well, come back when you do. I'll be waiting for you.",
				null);

		npc.add(ConversationStates.IDLE,
				ConversationPhrases.GREETING_MESSAGES,
				new QuestInStateCondition(QUEST_SLOT, "start"),
				ConversationStates.ATTENDING,
				"Have you found a sapphire?",
				null);

		npc.add(ConversationStates.IDLE,
				ConversationPhrases.GREETING_MESSAGES,
				new QuestInStateCondition(QUEST_SLOT, "done"),
				ConversationStates.IDLE,
				"How's your new pet holding up?",
				null);
	}

	@Override
	public void addToWorld() {
		fillQuestInfo(
				"Stealing pet thingy",
				"Give sapphire to NPC for a reward",
				true);
		firstStep();
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
