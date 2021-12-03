package games.stendhal.server.entity.creature;

import org.junit.Test;

import org.junit.BeforeClass;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

// Not much to be tested, these will have to do
public class StealingPetTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
	}
	
	List<String> foods = Arrays.asList("pinto beans", "roach", "egg");
	
	/**
	 * Tests for stealingPetDiet. Monkeys are omnivores.
	 */
	@Test
	public void testStealingPetDiet() {
		final StendhalRPZone zone = new StendhalRPZone("zone");
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		final StealingPet pet = new StealingPet(bob);
		assertThat(pet.getFoodNames(), is(foods));
	}
	
}
