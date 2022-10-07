package ec.com.edimca.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("app")
public class AppController {

    @GetMapping(value = "/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("Authentication running on port 8080", HttpStatus.OK);
    }
    
}
