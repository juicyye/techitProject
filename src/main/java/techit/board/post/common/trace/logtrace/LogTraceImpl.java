package techit.board.post.common.trace.logtrace;

import lombok.extern.slf4j.Slf4j;
import techit.board.post.common.trace.TraceId;
import techit.board.post.common.trace.TraceStatus;
@Slf4j
public class LogTraceImpl implements LogTrace{
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    private ThreadLocal<TraceId> traceHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceHolder.get();
        long startTimeMs = System.currentTimeMillis();

        TraceStatus traceStatus = new TraceStatus(traceId, message, startTimeMs);
        log.info("[{}] {}{}",traceId.getId(), addSpace(START_PREFIX,traceId.getLevel()),message);
        return traceStatus;

    }

    @Override
    public void end(TraceStatus status) {
        complete(status,null);

    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status,e);
    }

    private void complete(TraceStatus status, Exception e) {
        long endTimeMs = System.currentTimeMillis();
        long resultTimeMs = status.getStartTimeMs() - endTimeMs;
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms",traceId.getId(),addSpace(COMPLETE_PREFIX,traceId.getLevel()),status.getMessage(),resultTimeMs);
        } else{
            log.info("[{}] {}{} time={}ms",traceId.getId(),addSpace(EX_PREFIX,traceId.getLevel()),status.getMessage(),e.toString());
        }
        releaseTraceId();
    }

    private static String addSpace(String prefix, int level){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }

    private void syncTraceId(){
        TraceId traceId = traceHolder.get();
        if(traceId == null){
            traceHolder.set(new TraceId());
        } else{
            traceHolder.set(traceId.createNextId());
        }
    }

    private void releaseTraceId(){
        TraceId traceId = traceHolder.get();
        if (traceId.isFirstLevel()) {
            traceHolder.remove();
        } else{
            traceHolder.set(traceId.createPreviousId());
        }
    }
}
