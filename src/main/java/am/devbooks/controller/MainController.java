package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Book;
import am.devbooks.entity.Language;
import am.devbooks.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final LanguageService languageService;
    private final NoveltyService noveltyService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public MainController(LanguageService languageService, NoveltyService noveltyService,
                          BookService bookService, AuthorService authorService) {
        this.languageService = languageService;
        this.noveltyService = noveltyService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Book> books = noveltyService.getAllBook();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allLanguage", allLanguage);
        model.addAttribute("books", books);
        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        return "index";
    }

}
