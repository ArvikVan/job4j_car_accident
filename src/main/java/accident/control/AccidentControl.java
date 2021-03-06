package accident.control;

import accident.model.Accident;
import accident.model.AccidentType;
import accident.model.Rule;
import accident.repository.AccidentJdbcTemplate;
import accident.service.AccidentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ArvikV
 * @version 1.4
 * @since 31.01.2022
 * добавлен метод update
 * 1.4 Перенесите на уровень repository и храните так в Map
 * в методе create(Model model) убрал все лишнее, оставил обращение к атрибутам модели
 * Так же убрал в методе update(@RequestParam("id") int id, Model model) одинаковые строки, хотя
 * раньше без них не работало обновление/изменение аварий, на каждую строку приходился один параметр
 * который надо было менять
 */
@Controller
public class AccidentControl {
    private final AccidentService accidents;

    public AccidentControl(AccidentService accidents) {
        this.accidents = accidents;
    }

    /**
     * метод создания аварий
     * @return на выходе отправляемся на жсп для создания аварии
     * 1.3 добавили в контроллер список возможных типов
     * 1.4 добавили в контроллер список статей
     */
    @GetMapping("/create")
    public String create(Model model) {
        List<AccidentType> types = new ArrayList<>();
        model.addAttribute("types", accidents.getAccidentTypes());
        model.addAttribute("rules", accidents.getAccidentRules());
        return "accident/create";
    }

    /**
     * метод сохранения аварий
     * @param accident авария что надо сохранить
     * @return на выходе добавляем аварию к общей мапе(если провалиться видно)
     * и отправляемся на главную страницу
     * устанавливаем тип аварии по ид(из модели тип ид)
     * получаем данные на контроллере из запроса
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        accident.setType(accidents.findTypeId(accident.getType().getId()));
        String[] ids = req.getParameterValues("rIds");
        accident.setRules(accidents.getRules(ids));
        accidents.create(accident, ids);
        return "redirect:/";
    }

    /**
     * метод обновления
     * @param id ид что надо обновить
     * @param model изменяемая модел
     * @return на выходе жсп в которой будем работать
     * Загружаем в модель выбранную аварию model.addAttribute()
     */
    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidents.findById(id));
        model.addAttribute("types", accidents.getAccidentTypes());
        model.addAttribute("rules", accidents.getAccidentRules());
        return "accident/edit";
    }
}
