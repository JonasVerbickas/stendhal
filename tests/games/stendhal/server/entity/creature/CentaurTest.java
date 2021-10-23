package games.stendhal.server.entity.creature;

import org.junit.Test;
//import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;
//
import games.stendhal.common.constants.Nature;
import games.stendhal.server.core.engine.SingletonRepository;

public class CentaurTest {
	/**
	 * Test if solar centaur susceptibilities are configured correctly
	 */
	@Test
	public void testSolarCentaurSusceptibilities() {
		Creature solarCentaur = SingletonRepository.getEntityManager().getCreature("solar centaur");
		
		assertThat(solarCentaur.getSusceptibility(Nature.FIRE), closeTo(0.8, 0.00001));
		assertThat(solarCentaur.getSusceptibility(Nature.ICE), closeTo(1.2, 0.00001));
	}
	
	@Test
	public void testGlacierCentaurSusceptibilities() {
		Creature glacierCentaur = SingletonRepository.getEntityManager().getCreature("glacier centaur");
		
		assertThat(glacierCentaur.getSusceptibility(Nature.ICE), closeTo(0.8, 0.00001));
		assertThat(glacierCentaur.getSusceptibility(Nature.FIRE), closeTo(1.2, 0.00001));
	}
}
