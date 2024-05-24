package techit.board.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techit.board.post.entity.Board;
import techit.board.post.repository.BoardForm;
import techit.board.post.service.form.DeleteForm;
import techit.board.post.service.form.ModifyForm;
import techit.board.post.service.form.SearchForm;
import techit.board.post.service.BoardService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "offset", defaultValue = "0") int offset) {
        Page<Board> boards = boardService.findAll(new SearchForm(), offset);
        model.addAttribute("boards", boards);
        return "board/list";
    }


    @GetMapping("/search")
    public String boardList(@ModelAttribute("board") SearchForm form, Model model, @RequestParam(value = "offset", defaultValue = "0") int offset) {
        Page<Board> boards = boardService.findAll(form, offset);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/create")
    public String boardForm(@ModelAttribute("board") BoardForm form) {
        return "board/createForm";
    }

    @PostMapping("/create")
    public String createBoard(@Validated @ModelAttribute("board") BoardForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("create board error {}", bindingResult.getAllErrors());
            return "board/createForm";
        }
        boardService.save(form);
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        BoardForm form = boardService.findById(id);
        model.addAttribute("board", form);
        return "board/detail";
    }

    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable("id") Long id, Model model) {
        BoardForm form = boardService.findById(id);
        model.addAttribute("board", form);
        return "board/modifyForm";
    }

    @PostMapping("/modify/{id}")
    public String modify(@Validated @ModelAttribute("board") ModifyForm form, BindingResult bindingResult, RedirectAttributes rttr) {
        if (bindingResult.hasErrors()) {
            log.info("modify board error {}", bindingResult.getAllErrors());
            return "board/modifyForm";
        }
        BoardForm findPost = boardService.findById(form.getId());
        if (!findPost.getPassword().equals(form.getPassword())) {
            bindingResult.reject("invalidPassword", "비밀번호가 일치하지 않습니다.");
            return "board/modifyForm";
        }
        form.setPassword(findPost.getPassword());
        boardService.modifyBoard(form);
        rttr.addAttribute("modify",true);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id, Model model) {
        BoardForm form = boardService.findById(id);
        model.addAttribute("board", form);

        return "board/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@Validated @ModelAttribute("board") DeleteForm form, BindingResult bindingResult, RedirectAttributes rttr) {
        if (bindingResult.hasErrors()) {
            log.info("delete error {}", bindingResult.getAllErrors());
            return "board/delete";
        }
        BoardForm findBoard = boardService.findById(form.getId());
        if (!findBoard.getPassword().equals(form.getPassword())) {
            bindingResult.reject("invalidPassword", "비밀번호가 일치하지 않습니다.");
            return "board/delete";
        }
        boardService.deleteById(form.getId());
        rttr.addAttribute("delete", true);
        return "redirect:/";
    }

    @GetMapping("/allDelete")
    public String allDelete(){
        boardService.deleteAll();
        return "redirect:/";
    }
}
