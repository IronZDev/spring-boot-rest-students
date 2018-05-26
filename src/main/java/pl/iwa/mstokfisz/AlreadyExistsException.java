package pl.iwa.mstokfisz;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String exception) {
        super(exception);
    }

}