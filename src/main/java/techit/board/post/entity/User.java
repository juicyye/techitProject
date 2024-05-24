package techit.board.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import techit.board.post.service.form.JoinForm;
import techit.board.post.service.form.UserForm;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User implements Serializable {
    @Id
    private Long id;
    private String username;
    private String password;
    private String roles;

    /**
     * DTO -> ENTITY
     */
    public static User toEntity(JoinForm form) {
        return User.builder()
                .username(form.getUsername())
                .password(form.getPassword())
                .roles("ROLE_ADMIN")
                .build();
    }
}
