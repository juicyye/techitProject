package techit.board.post.service.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import techit.board.post.entity.User;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String roles;

    /**
     * ENTITY -> DTO
     */
    public static UserForm toDto(User user) {
        return UserForm.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}
