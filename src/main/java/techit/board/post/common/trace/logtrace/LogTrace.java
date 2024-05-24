package techit.board.post.common.trace.logtrace;

import techit.board.post.common.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
