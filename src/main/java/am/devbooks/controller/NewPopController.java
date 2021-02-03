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
public class NewPopController {
    private final LanguageService languageService;
    private final NoveltyService noveltyService;
    private final PopularService popularService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public NewPopController(LanguageService languageService, NoveltyService noveltyService,
                            PopularService popularService, AuthorService authorService, BookService bookService) {
        this.languageService = languageService;
        this.noveltyService = noveltyService;
        this.popularService = popularService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/new")
    public String showNewBook(Model model) {
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Book> books = noveltyService.getAllBook();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("books", books);
        model.addAttribute("allLanguage", allLanguage);
        return "index";
    }

    @GetMapping("/popular")
    public String showPopularBook(Model model) {
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Book> books = popularService.getAllBook();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("books", books);
        model.addAttribute("allLanguage", allLanguage);
        return "index";
    }
}
