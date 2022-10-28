package za.ac.cput.fms.service.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.domain.personal.Manager;
import za.ac.cput.fms.domain.personal.Referee;
import za.ac.cput.fms.repository.ManagerRepository;
import za.ac.cput.fms.repository.RefereeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RefereeService {

    @Autowired
    private RefereeRepository repository;

    public Referee save(Referee referee){
        return repository.save(referee);
    }

    public List<Referee> getAllReferees(){
        List<Referee> referees = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(referees::add);
        return referees;
    }

    public void deleteRefereeById(String id){
        repository.deleteById(id);
    }

    // TODO: getRefereeByTeamId
    public Referee getRefereeByTeamId(String teamId){
        return null;
    }

    public List<Referee> getRefereeById(String refereeId){
        if (null != refereeId){
            return repository.findAllById(Collections.singleton(refereeId));
        }
        else {
            return repository.findAll();
        }
    }

    public Referee updateReferee(String id, String fistName, String middleName, String lastName){
        Referee referee = repository.findById(id).get();

        referee.setFirstName(fistName);
        referee.setMiddleName(middleName);
        referee.setLastName(lastName);

        repository.save(referee);
        return referee;
    }

}
