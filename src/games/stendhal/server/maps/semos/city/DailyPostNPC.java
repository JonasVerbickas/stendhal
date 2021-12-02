package games.stendhal.server.maps.semos.city;

import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;


public class DailyPostNPC implements ZoneConfigurator{
	
	@Override
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	public void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("News Seller") {
			@Override
			public void createDialog() {
				// TODO
			}
		};
		// TODO
		zone.add(npc);
	}
}
