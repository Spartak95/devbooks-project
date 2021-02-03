package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Book;
import am.devbooks.entity.Language;
import am.devbooks.entity.Popular;
import am.devbooks.service.AuthorService;
import am.devbooks.service.BookService;
import am.devbooks.service.LanguageService;
import am.devbooks.service.PopularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class InfoController {

    private final BookService bookService;
    private final LanguageService languageService;
    private final PopularService popularService;
    private final AuthorService authorService;

    @Autowired
    public InfoController(BookService bookService, LanguageService languageService,
                          PopularService popularService, AuthorService authorService) {
        this.bookService = bookService;
        this.languageService = languageService;
        this.popularService = popularService;
        this.authorService = authorService;
    }

    @GetMapping("/info/{id}")
    public String showBookInfo(@PathVariable("id") int id, Model model) {
        Book bookById = bookService.getBookById(id);
        Popular popular = bookById.getPopular();
        int visit = popular.getVisit();
        popular.setVisit(++visit);
        popularService.savePopular(popular);
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("books", bookById);
        model.addAttribute("allLanguage", allLanguage);

        return "info";
    }

}

