package games.stendhal.server.entity.creature;

import org.junit.Test;

import org.junit.BeforeClass;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.common.game.RPAction;
import utilities.RPClass.BabyDragonTestHelper;

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
		final StealingPet pet = new StealingPet();
		assertThat(pet.getFoodNames(), is(foods));
	}
	
	/**
	 * Tests for stealingPetSpeed. We want it to be nimble.
	 */
	@Test
	public void testStealingPetSpeed()
	{
		final StealingPet pet = new StealingPet();
		assertEquals(pet.getDouble("base_speed"), 2, 0);
	}
	
}
