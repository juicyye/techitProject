package techit.board.post.common.trace;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;
@Getter
public class TraceId {
    private String id;
    private int level;

    public TraceId() {
        id = createId();
        level = 0;
    }

    public boolean isFirstLevel(){
        return level == 0;
    }
    public TraceId createNextId(){
        return new TraceId(id, level + 1);
    }
    public TraceId createPreviousId(){
        return new TraceId(id, level - 1);
    }


    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId(){
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
