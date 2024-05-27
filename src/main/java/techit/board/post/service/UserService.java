package techit.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.board.post.entity.User;
import techit.board.post.repository.UserRepository;
import techit.board.post.service.form.JoinForm;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(JoinForm form) {
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        User user = User.toEntity(form);
        userRepository.save(user);
    }

    public boolean isDuplicate(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        } else{
            return true;
        }
    }
}
