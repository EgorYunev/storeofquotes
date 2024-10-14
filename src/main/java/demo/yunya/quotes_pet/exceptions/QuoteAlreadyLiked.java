package demo.yunya.quotes_pet.exceptions;

public class QuoteAlreadyLiked extends RuntimeException {
    public QuoteAlreadyLiked(String message) {
        super(message);
    }
}
