package techit.board.post.exception;

public class NotFoundBoard extends RuntimeException{
    public NotFoundBoard(String message) {
        super(message);
    }
}
