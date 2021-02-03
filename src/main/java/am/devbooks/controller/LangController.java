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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LangController {

    private final LanguageService languageService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public LangController(LanguageService languageService, BookService bookService, AuthorService authorService) {
        this.languageService = languageService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("lang/{name}")
    public String getByProgLang(@PathVariable("name") String name, Model model) {
        return getPage(name, 1, model);
    }

    @GetMapping({"lang/{name}/page/{pageNumber}"})
    public String getPage(@PathVariable("name") String name, @PathVariable("pageNumber") int pageNumber, Model model) {
        Language langByName = languageService.getLanguageByName(name);
        String langName = langByName.getLangName();
        List<Language> allLanguage = languageService.getAllLanguage();
        Page<Book> page = bookService.getAllBook(pageNumber, langByName);
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();
        long totalItem = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("langName", langName);
        model.addAttribute("allLanguage", allLanguage);
        model.addAttribute("langByName", langByName);
        model.addAttribute("totalItems", totalItem);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("books", page);
        model.addAttribute("i", pageNumber);
        return "lang";
    }

    @PostMapping("/addLang")
    public String addLang(@RequestParam("langName") String langName) {
        Language language = new Language(langName);
        languageService.createLanguage(language);
        return "redirect:/";
    }

    @PostMapping("/deleteLang")
    public String deleteLang(@RequestParam("langName") int id) {
        languageService.deleteLanguageById(id);
        return "redirect:/";
    }
}
