package za.ac.cput.fms.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.fms.domain.personal.Manager;
import za.ac.cput.fms.domain.personal.Referee;
import za.ac.cput.fms.service.personal.RefereeService;

import java.util.List;

@RestController
public class RefereeController {


    @Autowired
    private RefereeService service;

    @GetMapping("/fms/referee/all")
    public List<Referee> getAllReferees(){
        return service.getAllReferees();
    }

    @GetMapping("/fms/referee/{refereeId}")
    public List<Referee> getRefereeById(@PathVariable(required = false) String refereeId){
        return service.getRefereeById(refereeId);
    }

    @PostMapping("/fms/referee/save")
    public Referee save(@RequestBody Referee referee){
        return service.save(referee);
    }

    @DeleteMapping("/fms/referee/{refereeId}/delete")
    public ResponseEntity deleteManagerById(@PathVariable String refereeId){
        service.deleteRefereeById(refereeId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/fms/referee/{refereeId}/update")
    public Referee updateReferee(@PathVariable String refereeId, String fName, String mName, String lName){
        return service.updateReferee(refereeId, fName, mName, lName);
    }

}
