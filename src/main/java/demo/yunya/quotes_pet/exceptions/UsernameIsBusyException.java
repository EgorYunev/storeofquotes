package demo.yunya.quotes_pet.exceptions;

public class UsernameIsBusyException extends RuntimeException {
    public UsernameIsBusyException() {
        super("Пользователь с таким именем уже существует");
    }
}
