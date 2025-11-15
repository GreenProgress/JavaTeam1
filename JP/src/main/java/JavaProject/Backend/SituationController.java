package JavaProject.Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Situation")
public class SituationController {

    @Autowired
    private SituationRepository repo;

    @GetMapping("/all")
    public List<Situation> getAll() {
        return repo.findAll();
    }
}
