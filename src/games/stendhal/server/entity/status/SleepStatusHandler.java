/***************************************************************************
 *                   (C) Copyright 2013 - Faiumoni e. V.                   *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.status;

import games.stendhal.common.NotificationType;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;

/**
 * handles SleepStatus
 */
public class SleepStatusHandler implements StatusHandler<SleepStatus> {

	/**
	 * inflicts a status
	 *
	 * @param status Status to inflict
	 * @param statusList StatusList
	 * @param attacker the attacker
	 */
	@Override
	public void inflict(SleepStatus status, StatusList statusList, Entity attacker) {
		if (statusList.hasStatus(status.getStatusType())) {
			return;
		}

		RPEntity entity = statusList.getEntity();
		if (entity == null) {
			return;
		}

		if(statusList.hasStatus(StatusType.SLEEPING)) {
			entity.sendPrivateText(NotificationType.SCENE_SETTING, "You are already asleep.");
			return;
		}		

		entity.sendPrivateText(NotificationType.SCENE_SETTING, "You are asleep.");

		statusList.addInternal(status);
		statusList.activateStatusAttribute("status_" + status.getName());
	
		TurnListener turnListener = new SleepStatusTurnListener(statusList);
		TurnNotifier.get().dontNotify(turnListener);
		TurnNotifier.get().notifyInTurns(0, turnListener);
		
	}

	/**
	 * removes a status
	 *
	 * @param status Status to inflict
	 * @param statusList StatusList
	 */
	@Override
	public void remove(SleepStatus status, StatusList statusList) {
		statusList.removeInternal(status);

		RPEntity entity = statusList.getEntity();
		if (entity == null) {
			return;
		}

		entity.sendPrivateText(NotificationType.SCENE_SETTING, "You are no longer alseep.");
		entity.remove("status_" + status.getName());
	}
}
