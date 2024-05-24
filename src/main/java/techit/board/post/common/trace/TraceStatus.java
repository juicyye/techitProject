package techit.board.post.common.trace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceStatus {
    private TraceId traceId;
    private String message;
    private long startTimeMs;


}
