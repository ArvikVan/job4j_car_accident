package accident.control;

import accident.model.Accident;
import accident.repository.AccidentHibernate;
import accident.repository.AccidentJdbcTemplate;
import accident.repository.AccidentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ArvikV
 * @version 1.0
 * @since 30.01.2022
 */
@Controller
public class IndexControl {
    private final AccidentRepository accidentService;

    public IndexControl(AccidentRepository accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Accident> res = new ArrayList<>();
        accidentService.findAll().forEach(res::add);
        model.addAttribute("NameOfRefToJSP", res);
        return "index";
    }
}
