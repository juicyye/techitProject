package techit.board.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import techit.board.post.service.UserService;
import techit.board.post.service.form.JoinForm;
import techit.board.post.service.form.UserForm;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login";
    }

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("user")JoinForm form){
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") JoinForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.info("join error {}" + bindingResult.getAllErrors());
            return "user/join";
        }
        if (!form.getPassword().equals(form.getPassword2())) {
            bindingResult.reject("incorrect password","비밀번호가 일치하지 않습니다.");
            return "user/join";
        }
        if (userService.isDuplicate(form.getUsername())) {
            bindingResult.reject("username duplicate", "이미 존재하는 아이디입니다.");
            return "user/join";
        }
        userService.join(form);
        return "redirect:/";
    }
}
