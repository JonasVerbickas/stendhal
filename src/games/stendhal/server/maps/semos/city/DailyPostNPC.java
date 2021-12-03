package games.stendhal.server.maps.semos.city;

import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.SayTextAction;


public class DailyPostNPC implements ZoneConfigurator{
	
	@Override
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	public void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("News Seller") {
			@Override
			public void createDialog() {
				addJob("I'm the news seller agent and I can inform you about the daily post.");
				addOffer("I can show you the daily post");
		
				addGreeting(null, new SayTextAction("Hi, there! Here you can find the #main achievements by the players or check #secondary ones"));
				addGoodbye();
			}
		};
		
		npc.setPosition(37, 53);
		npc.setEntityClass("oldmannpc");
		npc.setDescription("You see the news seller. He can show you the daily post");
		npc.setDirection(Direction.LEFT);
		zone.add(npc);
	}
}