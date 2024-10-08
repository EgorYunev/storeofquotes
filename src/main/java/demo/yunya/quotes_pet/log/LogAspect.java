package demo.yunya.quotes_pet.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(Log)")
    public void pointLog() {
    }

    @AfterReturning(pointcut = "pointLog()", returning = "value")
    public void afterRet(JoinPoint jp, Object value) {
        System.out.println(jp.getSignature().toShortString() + " : " + value);
    }

    @AfterThrowing(pointcut = "pointLog", throwing = "ex")
    public void afterThrow(RuntimeException ex) {
        ex.getStackTrace();
    }

}
