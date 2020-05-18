package server.bl.services.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.bl.services.MailService;
import server.entities.document.DocumentDto;
import server.entities.user.UserDto;

@Aspect
@Component
public class DocumentServiceAfterUploadAspect {

    @Autowired
    private MailService mailService;

    @AfterReturning(value = "execution(* server.bl.services.DocumentService.saveDocument(..))", returning = "result")
    public void sendEmail(JoinPoint point, Object result) {
        if (result != null) {
            UserDto userDto = (UserDto) point.getArgs()[1];
            DocumentDto fileModel = (DocumentDto) result;

            mailService.send("Ваш файл", "pologies12314s@mail.ru", userDto.getMail(), String.valueOf(fileModel.getId()), fileModel.getOriginalName());
        }
    }
}
