package ec.com.edimca.authentication.util;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;
    private Date expiresIn;

}
