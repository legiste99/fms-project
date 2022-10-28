package za.ac.cput.fms.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.fms.domain.personal.Player;
import za.ac.cput.fms.service.personal.PlayerService;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService service;

    @GetMapping("/fms/player/all")
    public List<Player> getAllPlayers(){
        return service.getAllPlayers();
    }

    @GetMapping("/fms/player/{playerId}")
    public List<Player> getPlayerById(@PathVariable(required = false) String playerId){
        return service.getPlayerById(playerId);
    }

    @PostMapping("/fms/player/save")
    public Player save(@RequestBody Player player){
        return service.save(player);
    }

    @DeleteMapping("/fms/player/{playerId}/delete")
    public ResponseEntity deletePlayerById(@PathVariable String playerId){
        service.deletePlayerById(playerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/fms/player/{playerId}/update")
    public Player updatePlayer(@PathVariable String playerId, String firsName, String middleName, String lastName, String position, int positionNumber){
        return service.updatePlayer(playerId, firsName, middleName, lastName, position, positionNumber);
    }



}
