package demo.yunya.quotes_pet.services;

import demo.yunya.quotes_pet.models.Quote;
import demo.yunya.quotes_pet.repositories.QuoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuoteService {

    private QuoteRepository repo;

    public void addQuote(Quote quote) {
        repo.save(quote);
    }

    public void changeQuote(Quote quote) {
        repo.save(quote);
    }

    public void deleteQuoteById(int id) {
        repo.deleteById(id);
    }

    public List<Quote> getAllQuote() {
        return repo.findAll();
    }

    public List<Quote> getAllByUsername(String username) {
        return repo.findAllByAuthor_Username(username);
    }

}
