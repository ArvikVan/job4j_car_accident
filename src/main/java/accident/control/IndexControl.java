package accident.control;

import accident.repository.AccidentJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ArvikV
 * @version 1.0
 * @since 30.01.2022
 */
@Controller
public class IndexControl {
    private final AccidentJdbcTemplate accidentService;

    public IndexControl(AccidentJdbcTemplate accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("NameOfRefToJSP", accidentService.getAccidents());
        return "index";
    }
}
