package za.ac.cput.fms;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.fms.domain.game.Tournament;
import za.ac.cput.fms.service.game.TournamentService;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FmsServerApplicationTests {


	@Autowired
	private TournamentService service;

	@BeforeAll
	public void clear(){
		List<Tournament> tournaments = service.getAllTournaments();
		for (Tournament t: tournaments){
			service.deleteById(t.getId());
		}
	}
	@Order(1)
	@Test
	void addTournamentTest(){
		Tournament tournament = new Tournament.Builder()
				.setId(generateId())
						.setTournamentName("Fake Soccer League")
								.setNumberOfTeams(32)
										.build();
		service.save(tournament);
	}
	@Order(2)
	@Test
	void getAllTournaments(){
		List<Tournament> tournaments = service.getAllTournaments();
		System.out.println(tournaments);
	}

	private String generateId(){
		return RandomStringUtils.random(8, "0123456789abcdef");
	}

}
