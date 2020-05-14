package server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.bl.services.rest.SignInRestService;
import server.entities.signIn.dto.SignInDto;
import server.entities.token.dto.TokenDto;

@RestController
public class SignInRestController {

    @Autowired
    private SignInRestService signInService;

    @PostMapping(value = "/api/signIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto){

        return ResponseEntity.ok(signInService.signIn(signInDto));
    }
}
