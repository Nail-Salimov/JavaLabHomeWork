package server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import server.bl.services.UserService;
import server.bl.services.rest.SignInRestService;
import server.entities.signIn.dto.SignInDto;
import server.entities.token.dto.TokenDto;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SignInRestController {

    @Autowired
    private SignInRestService signInService;

    @PostMapping(value = "/api/signIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto){

        return ResponseEntity.ok(signInService.signIn(signInDto));
    }
}
