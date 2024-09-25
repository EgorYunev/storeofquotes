package demo.yunya.quotes_pet.exceptions;

public class UsernameIsBusy extends RuntimeException {
    public UsernameIsBusy() {
        super("Пользователь с таким именем уже существует");
    }
}
