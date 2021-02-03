package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Book;
import am.devbooks.entity.Language;
import am.devbooks.service.AuthorService;
import am.devbooks.service.BookService;
import am.devbooks.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SearchController {

    private final LanguageService languageService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public SearchController(LanguageService languageService, BookService bookService, AuthorService authorService) {
        this.languageService = languageService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/search")
    public String search(Model model, @Param("keyword") String keyword, @Param("i") Integer i) {

        if (i == null) i = 1;

        List<Language> allLanguage = languageService.getAllLanguage();
        Page<Book> page = bookService.getAllBook(keyword, i);
        long totalItem = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("allLanguage", allLanguage);
        model.addAttribute("totalItems", totalItem);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("books", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("i", i);

        return "index";
    }
}
