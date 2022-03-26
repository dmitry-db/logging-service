package liga.medical.logging.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import liga.medical.logging.model.LogEntity;
import liga.medical.logging.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Aspect
public class LoggingAdviceForSecurity {

    private final LogService logService;

    public LoggingAdviceForSecurity(@Qualifier("logServiceImpl")LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(public * loadUserByUsername(String))")
    public void publicMethodAuthorization() {
    }

    @Around("publicMethodAuthorization()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws JsonProcessingException {
        Object[] array = pjp.getArgs();
        LogEntity loger = new LogEntity("tryAuthentication", LocalDateTime.now(), null, null,
                array[0].toString());
        log.info("Дата и время - {} : имя пользователя пытающегося выполнить вход - {}",
                loger.getDateTime(), loger.getNameUser());

        logService.save(loger);
        Object object = null;
        try {
            object = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return object;
    }
}
