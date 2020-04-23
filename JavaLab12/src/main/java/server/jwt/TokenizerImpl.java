package server.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import server.entities.dto.UserDto;
import server.entities.model.UserModel;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Component
public class TokenizerImpl implements Tokenizer {

    private final static String secretWord = "6hdl3Mkkj75E0zGD";

    @Override
    public String getToken(String name, String email) {
        try {
            return Jwts.builder().setSubject("afmch0832yc")
                    .claim("name", name)
                    .claim("email", email)
                    .signWith(SignatureAlgorithm.HS256, secretWord.getBytes("UTF-8")).compact();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean checkClient(String token, String name, String email) {

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretWord.getBytes("UTF-8"))
                    .parseClaimsJws(token);

            String tokenUsername = (String) claims.getBody().get("name");
            String tokenEmail = (String) claims.getBody().get("email");

            return tokenUsername.equals(name)
                            && tokenEmail.equals(email);

        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    @Override
    public Optional<UserDto> getUser(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretWord.getBytes("UTF-8"))
                    .parseClaimsJws(token);

            String tokenUsername = (String) claims.getBody().get("name");
            String tokenEmail = (String) claims.getBody().get("email");

           return Optional.of(UserDto.builder().mail(tokenEmail).name(tokenUsername).build());

        } catch (UnsupportedEncodingException e) {
            return Optional.empty();
        }
    }

}
