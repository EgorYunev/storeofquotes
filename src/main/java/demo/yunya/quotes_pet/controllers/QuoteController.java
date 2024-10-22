package demo.yunya.quotes_pet.controllers;

import demo.yunya.quotes_pet.log.Log;
import demo.yunya.quotes_pet.models.AppUser;
import demo.yunya.quotes_pet.models.Quote;
import demo.yunya.quotes_pet.services.AppUserService;
import demo.yunya.quotes_pet.services.QuoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/quotes")
@Log
public class QuoteController {

    private QuoteService service;

    private AppUserService appUserService;

    @PostMapping("/save")
    public String addQuote(@RequestParam String text, Principal principal, Model model) {
        int userid = appUserService.getUserByUsername(principal.getName()).getId();
        Quote quote = Quote.builder()
                .author(appUserService.getUserById(userid))
                .text(text)
                .build();
        service.addQuote(quote);
        appUserService.addQuoteToUserList(quote, userid);
        model.addAttribute("message", "Цитата добавлена");
        return "success";
    }

    @GetMapping("/get-all")
    public String getAllQuotes(Model model) {
        List<Quote> list = service.getAllQuote();
        list.sort((q1, q2) -> Integer.compare(q2.getLikes(), q1.getLikes()));
        model.addAttribute("quotes", list);

        return "main";
    }

    @GetMapping("/get-all-by-username")
    public String getAllByUsername(@RequestParam String username, Model model) {
        List<Quote> quotes = service.getAllByUsername(username);
        if (quotes == null) {
            model.addAttribute("message", "Пользователь не найден!");
            return "error";
        } else if (quotes.isEmpty()) {
            model.addAttribute("message", "У пользователя нет сохраненных цитат");
            return "error";
        } else {
            model.addAttribute("quotes", quotes);
            return "get-all-by-username";
        }
    }

    @PostMapping("/like")
    public String like(@RequestParam int id, Principal principal, Model model) {
        Optional<Quote> quoteOptional = service.getQuoteById(id);
        if (quoteOptional.isPresent()) {
            Quote quote = quoteOptional.get();
            model.addAttribute("quote", quote);
            AppUser user = appUserService.getUserByUsername(principal.getName());
            List<Quote> likesQuotes = user.getLikesQuotes();
            for (Quote q : likesQuotes) {
                if (q.equals(quote)) {
                    model.addAttribute("message", "Цитата уже была лайкнута!");
                    return "error";
                }
            }
            quote.setLikes(quote.getLikes() + 1);
            likesQuotes.add(quote);
            service.addQuote(quote);
            appUserService.changeUser(user);
        } else {
            model.addAttribute("message", "Цитата не найдена");
            return "error";
        }
        model.addAttribute("message", "Лайк поставлен!");
        return "success";
    }


    @PostMapping("/delete")
    public String deleteQuote(@RequestParam int id, Principal principal, Model model) {
        List<Quote> list = appUserService.getUserByUsername(principal.getName()).getQuotes();
        Optional<Quote> op = service.getQuoteById(id);
        if(op.isPresent()) {
            Quote quote = op.get();
            for (Quote q : list) {
                if (q.getId() == quote.getId()) {
                    list.remove(q);
                    service.deleteQuoteById(id);
                    model.addAttribute("message", "Цитата удалена");
                    return "success";
                }
            }
        }
        model.addAttribute("message", "У вас нет прав доступа к этой цитате");
        return "error";
    }
}
