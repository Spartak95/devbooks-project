package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Book;
import am.devbooks.entity.Language;
import am.devbooks.service.AuthorService;
import am.devbooks.service.BookService;
import am.devbooks.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FilterController {

    private final LanguageService languageService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public FilterController(LanguageService languageService, BookService bookService, AuthorService authorService) {
        this.languageService = languageService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(value = "author", required = false) String author,
                         @RequestParam(value = "lang", required = false) String lang,
                         @RequestParam(value = "i", required = false) Integer i, Model model) {

        if (i == null) {
            i = 1;
        } else if (author != null) {
            if (author.equals("")) author = null;
        } else if (lang != null) {
            if (lang.equals("")) lang = null;
        }

        Page<Book> filter = bookService.getFilter(author, lang, i);
        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();
        long totalItem = filter.getTotalElements();
        int totalPages = filter.getTotalPages();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("allLanguage", allLanguage);
        model.addAttribute("books", filter);
        model.addAttribute("totalItem", totalItem);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("author", author);
        model.addAttribute("lang", lang);
        model.addAttribute("i", i);

        return "index";
    }
}
