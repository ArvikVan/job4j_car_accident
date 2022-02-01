package accident.control;

import accident.model.Accident;
import accident.repository.AccidentMem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ArvikV
 * @version 1.
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
     */
    @GetMapping("/create")
    public String create() {
        return "accident/create";
    }

    /**
     * метод сохранения аварий
     * @param accident авария что надо сохранить
     * @return на выходе добавляем аварию к общей мапе(если провалиться видно)
     * и отправляемся на главную страницу
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
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
        return "accident/edit";
    }
}
