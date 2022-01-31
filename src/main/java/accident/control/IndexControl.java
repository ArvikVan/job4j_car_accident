package accident.control;

import accident.service.AccidentService;
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
    private final AccidentService accidentService;

    public IndexControl(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("NameOfRefToJSP", accidentService.getAccident());
        return "index";
    }
}
