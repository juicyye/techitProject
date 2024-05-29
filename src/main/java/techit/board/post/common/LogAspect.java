package techit.board.post.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import techit.board.post.common.trace.TraceStatus;
import techit.board.post.common.trace.logtrace.LogTrace;

@Aspect

public class LogAspect {
    private LogTrace logTrace;

    public LogAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Pointcut("execution(* techit.board.post..*(..)) && !target(techit.board.post.WebConfig) && !execution(* techit.board.post.common..*(..))")
    public void post(){}
    @Around("post()")
    public Object logTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            status = logTrace.begin(joinPoint.getSignature().getName());
            Object result = joinPoint.proceed();
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.end(status);
            throw e;
        }
    }
}
