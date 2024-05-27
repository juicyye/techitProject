package techit.board.post.common.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import techit.board.post.entity.User;
import techit.board.post.repository.UserRepository;
import techit.board.post.service.form.UserContext;
import techit.board.post.service.form.UserForm;

import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
public class FormUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("not found username: " + username);
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRoles()));
        UserForm form = UserForm.toDto(user);
        return new UserContext(form, authorities);
    }
}
