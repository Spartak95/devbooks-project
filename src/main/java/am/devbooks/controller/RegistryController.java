package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Language;
import am.devbooks.entity.User;
import am.devbooks.service.AuthorService;
import am.devbooks.service.BookService;
import am.devbooks.service.LanguageService;
import am.devbooks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RegistryController {

    private final LanguageService languageService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final UserService userService;

    @Autowired
    public RegistryController(LanguageService languageService, BookService bookService,
                              AuthorService authorService, UserService userService) {
        this.languageService = languageService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model, User user) {
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("allLanguage", allLanguage);
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/process_register")
    public String processRegistration(@Valid User user, BindingResult bindingResult,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
                                      @RequestParam(value = "email", required = false) String email,
                                      Model model) {

        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        User u = userService.getEmail(email);

        if (!password.equals(confirmPassword)) {

            model.addAttribute("allAuthor", allAuthor);
            model.addAttribute("allBookLang", allBookLang);
            model.addAttribute("allLanguage", allLanguage);
            model.addAttribute("confirmError", true);

            return "register";
        } else if (u != null) {

            model.addAttribute("allAuthor", allAuthor);
            model.addAttribute("allBookLang", allBookLang);
            model.addAttribute("allLanguage", allLanguage);
            model.addAttribute("emailError", true);

            return "register";
        } if (bindingResult.hasErrors()) {

            model.addAttribute("allAuthor", allAuthor);
            model.addAttribute("allBookLang", allBookLang);
            model.addAttribute("allLanguage", allLanguage);

            return "register";
        } else {

            model.addAttribute("allAuthor", allAuthor);
            model.addAttribute("allBookLang", allBookLang);
            model.addAttribute("allLanguage", allLanguage);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encode = passwordEncoder.encode(user.getPassword());
            user.setRole("ROLE_USER");
            user.setPassword(encode);
            userService.save(user);

            return "register_success";
        }
    }
}
