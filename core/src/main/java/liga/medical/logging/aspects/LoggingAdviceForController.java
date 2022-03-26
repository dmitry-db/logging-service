package liga.medical.logging.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import liga.medical.logging.model.LogEntity;
import liga.medical.logging.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class LoggingAdviceForController {

    private final LogService logService;

    public LoggingAdviceForController(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("within(@liga.medical.logging.annotation.Loggable *)")
    public void beanAnnotatedWithMonitor() {

    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {

    }

    @Pointcut("@annotation(liga.medical.logging.annotation.Loggable)")
    public void annotationMethod() {

    }

    @Around("beanAnnotatedWithMonitor() && publicMethod() || annotationMethod()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws JsonProcessingException {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] args = pjp.getArgs();
        LogEntity loger = new LogEntity("rest", LocalDateTime.now(), methodName, className,
                ((Authentication) args[1]).getName());
        log.info("Дата и время - {} : имя метода {} : Полное имя класса - {} : имя пользователя - {}",
                loger.getDateTime(), loger.getNameMethod(), loger.getFullNameClass(),
                loger.getNameUser());
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
