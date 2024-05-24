package techit.board.post.repository;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import techit.board.post.entity.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class BoardForm {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public static List<BoardForm> createBoardForm(List<Board> boards) {
        List<BoardForm> forms = new ArrayList<>();
        for (Board board : boards) {
            forms.add(createBoardForm(board));
        }
        return forms;
    }

    public static BoardForm createBoardForm(Board board) {
        return BoardForm.builder()
                .id(board.getId())
                .name(board.getName())
                .title(board.getTitle())
                .content(board.getContent())
                .password(board.getPassword())
                .created_at(board.getCreated_at())
                .updated_at(board.getUpdated_at())
                .build();
    }

}
