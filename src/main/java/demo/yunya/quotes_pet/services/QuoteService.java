package demo.yunya.quotes_pet.services;

import demo.yunya.quotes_pet.models.Quote;
import demo.yunya.quotes_pet.repositories.QuoteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuoteService {

    private QuoteRepository repo;

    public Optional<Quote> getQuoteById(int id) {
        return repo.findById(id);
    }

    public void addQuote(Quote quote) {
        repo.save(quote);
    }

    public void changeQuote(Quote quote) {
        repo.save(quote);
    }

    @Transactional
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
