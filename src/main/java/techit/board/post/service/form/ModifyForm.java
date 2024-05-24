package techit.board.post.service.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyForm {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String password;

}
