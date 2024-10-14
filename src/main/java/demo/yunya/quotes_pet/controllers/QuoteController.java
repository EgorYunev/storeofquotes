package demo.yunya.quotes_pet.controllers;

import demo.yunya.quotes_pet.exceptions.QuoteAlreadyLiked;
import demo.yunya.quotes_pet.exceptions.QuoteNotFountException;
import demo.yunya.quotes_pet.exceptions.UserCantBeFindException;
import demo.yunya.quotes_pet.log.Log;
import demo.yunya.quotes_pet.models.AppUser;
import demo.yunya.quotes_pet.models.Quote;
import demo.yunya.quotes_pet.services.AppUserService;
import demo.yunya.quotes_pet.services.QuoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/quotes")
@Log
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

    @PutMapping("/{id}/{userId}/like")
    public void like(@PathVariable int id, @PathVariable int userId) {

        Optional<Quote> quoteOptional = service.getQuoteById(id);
        if (quoteOptional.isPresent()) {
            Quote quote = quoteOptional.get();
            AppUser user = appUserService.getUserById(userId);
            List<Quote> likesQuotes = user.getLikesQuotes();
            for (Quote q : likesQuotes) {
                if (q.equals(quote)) {
                    throw new QuoteAlreadyLiked(Integer.toString(quote.getId()));
                }
            }
            quote.setLikes(quote.getLikes() + 1);
            likesQuotes.add(quote);
            service.addQuote(quote);
            appUserService.changeUser(user);
        } else {
            throw new QuoteNotFountException(String.valueOf(id));
        }
    }

    @DeleteMapping("/delete")
    public String deleteQuote(@RequestBody String id) {
        service.deleteQuoteById(Integer.parseInt(id));
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
