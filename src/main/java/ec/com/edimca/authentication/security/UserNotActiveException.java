package ec.com.edimca.authentication.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotActiveException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UserNotActiveException(String msg) {
        super(msg);
    }

    public UserNotActiveException(String msg, Throwable t) {
        super(msg, t);
    }

}
