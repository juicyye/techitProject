package techit.board.post.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import techit.board.post.repository.BoardForm;

import java.time.LocalDateTime;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {
    @Id
    private Long id;
    private String name;
    private String title;
    private String content;
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public void change(String title, String content, String password) {
        this.title = title;
        this.content = content;
        this.password = password;
    }

    /**
     * DTO -> ENTITY
     */
    public static Board cretaeBoard(BoardForm form) {
        return Board.builder()
                .name(form.getName())
                .title(form.getTitle())
                .content(form.getContent())
                .password(form.getPassword())
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();
    }
}
