package demo.yunya.quotes_pet.exceptions;

public class UserCantBeFindException extends RuntimeException {
    public UserCantBeFindException() {
        super("Пользователь не может быть найден");
    }
}
