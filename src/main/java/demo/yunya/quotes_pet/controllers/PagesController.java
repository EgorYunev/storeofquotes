package demo.yunya.quotes_pet.controllers;

import demo.yunya.quotes_pet.DTO.AppUserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PagesController {

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("dto", new AppUserDto());
        return "registration";
    }

}
