package mk.frizer.model.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends RuntimeException{

    public EmailNotFoundException(String username) {
        super(String.format("User with email: %s was not found", username));
    }
}
