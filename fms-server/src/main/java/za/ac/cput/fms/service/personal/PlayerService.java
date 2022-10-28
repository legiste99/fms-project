package za.ac.cput.fms.service.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.domain.personal.Player;
import za.ac.cput.fms.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository repository;

    public Player save(Player player){
        player.setTotalGoalsScored(0);
        player.setTotalAssistsMade(0);
        return repository.save(player);
    }

    public List<Player> getAllPlayers(){
        List<Player> players = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(players::add);
        return players;
    }

    public void deletePlayerById(String id){
        repository.deleteById(id);
    }

    public List<Player> getPlayerById(String id){
        if (null != id){
            return repository.findAllById(Collections.singleton(id));
        }
        else {
            return repository.findAll();
        }
    }

    public Player updatePlayer(String id, String firstName, String middleName, String lastName, String position, int positionNumber){

        Player player = repository.findById(id).get();
        player.setFirstName(firstName);
        player.setMiddleName(middleName);
        player.setLastName(lastName);
        player.setPosition(position);
        player.setPositionNumber(positionNumber);

        return repository.save(player);

    }


}
