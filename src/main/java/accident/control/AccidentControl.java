package accident.control;

import accident.model.Accident;
import accident.model.AccidentType;
import accident.repository.AccidentMem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ArvikV
 * @version 1.3
 * @since 31.01.2022
 * добавлен метод update
 */
@Controller
public class AccidentControl {
    private final AccidentMem accidents;

    public AccidentControl(AccidentMem accidents) {
        this.accidents = accidents;
    }

    /**
     * метод создания аварий
     * @return на выходе отправляемся на жсп для создания аварии
     * 1.3 добавили в контроллер список возможных типов
     */
    @GetMapping("/create")
    public String create(Model model) {
        List<AccidentType> types = new ArrayList<>();
        types.add(AccidentType.of(1, "Two cars"));
        types.add(AccidentType.of(2, "Human and vehicle"));
        types.add(AccidentType.of(3, "Vehicle and bycicle"));
        model.addAttribute("types", types);
        return "accident/create";
    }

    /**
     * метод сохранения аварий
     * @param accident авария что надо сохранить
     * @return на выходе добавляем аварию к общей мапе(если провалиться видно)
     * и отправляемся на главную страницу
     * устанавливаем тип аварии по ид(из модели тип ид)
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accident.setType(accidents.findTypeId(accident.getType().getId()));
        accidents.create(accident);
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
        model.addAttribute("accident", accidents.findById(id));
        model.addAttribute("accident", accidents.findById(id));
        model.addAttribute("types", accidents.getAccidentTypes());
        return "accident/edit";
    }
}
