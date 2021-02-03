package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Language;
import am.devbooks.entity.User;
import am.devbooks.service.AuthorService;
import am.devbooks.service.BookService;
import am.devbooks.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    private final LanguageService languageService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public LoginController(LanguageService languageService, BookService bookService, AuthorService authorService) {
        this.languageService = languageService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/login")
    public String login(Model model, User user) {
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("allLanguage", allLanguage);
        model.addAttribute("user", user);

        return "login";
    }

    @PostMapping("/fail_login")
    public String failLogin(Model model) {

        model.addAttribute("error", true);

        return "login";
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
