package techit.board.post;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import techit.board.post.common.LogAspect;
import techit.board.post.common.trace.logtrace.LogTrace;
import techit.board.post.common.trace.logtrace.LogTraceImpl;

@Configuration
public class WebConfig {
    @Bean
    public LogTrace logTrace(){
        return new LogTraceImpl();
    }
    @Bean
    public LogAspect logAspect(){
        return new LogAspect(logTrace());
    }
}
