package demo.yunya.quotes_pet.exceptions;

public class UserCantBeFind extends RuntimeException {
    public UserCantBeFind() {
        super("Пользователь не может быть найден");
    }
}
