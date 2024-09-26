package demo.yunya.quotes_pet.controllers;

import demo.yunya.quotes_pet.exceptions.QuoteNotFountException;
import demo.yunya.quotes_pet.exceptions.UserCantBeFindException;
import demo.yunya.quotes_pet.models.Quote;
import demo.yunya.quotes_pet.services.AppUserService;
import demo.yunya.quotes_pet.services.QuoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/quotes")
public class QuoteController {

    private QuoteService service;

    private AppUserService appUserService;

    @PostMapping("/save/{userid}")
    public String addQuote(@RequestBody String text, @PathVariable int userid) {
        Quote quote = Quote.builder()
                .author(appUserService.getUserById(userid))
                .text(text)
                .build();

        service.addQuote(quote);

        appUserService.addQuoteToUserList(quote, userid);

        return "Цитата успешно добавлена";
    }

    @GetMapping("/get-all")
    public List<Quote> getAllQuotes() {
        return service.getAllQuote();
    }

    @GetMapping("/get-all-by-username")
    public List<Quote> getAllByUsername(@RequestBody String username) {
        List<Quote> quotes = service.getAllByUsername(username);
        if (quotes == null) {
            throw new UserCantBeFindException();
        }
        else if (quotes.isEmpty()) {
            throw new QuoteNotFountException("У пользователя нет сохраненных цитат");
        } else {
            return quotes;
        }
    }

    @DeleteMapping("/delete")
    public String deleteQuote(@RequestBody int id) {
        service.deleteQuoteById(id);
        return "Цитата успешно удалена";
    }

    @PutMapping("/change/{userid}/{id}")
    public String changeQuote(@PathVariable int id, @RequestBody String text, @PathVariable int userid) {
        Quote quote = Quote.builder()
                .text(text)
                .id(id)
                .author(appUserService.getUserById(userid))
                .build();
        service.changeQuote(quote);
        return "Цитата успешно изменена";
    }

}
