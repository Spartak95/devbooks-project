package am.devbooks.controller;

import am.devbooks.entity.Author;
import am.devbooks.entity.Language;
import am.devbooks.entity.User;
import am.devbooks.other.Utility;
import am.devbooks.service.AuthorService;
import am.devbooks.service.BookService;
import am.devbooks.service.LanguageService;
import am.devbooks.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ForgotPasswordController {

    private final LanguageService languageService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final UserService userService;
    private final JavaMailSender mailSender;

    @Autowired
    public ForgotPasswordController(LanguageService languageService, BookService bookService,
                                    AuthorService authorService, UserService userService,
                                    JavaMailSender mailSender) {
        this.languageService = languageService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {

        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("allLanguage", allLanguage);

        return "forgot_password";
    }

    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(User user, Model model, HttpServletRequest request) {

         List<Language> allLanguage = languageService.getAllLanguage();
         List<Author> allAuthor = authorService.getAllAuthor();
         List<String> allBookLang = bookService.getBookLang();

         String token = RandomString.make(64);
         String email = user.getEmail();

         try {

              userService.updateResetPassword(token, email);
              String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;

              sendEmail(email, resetPasswordLink);

              model.addAttribute("message", true);

         } catch (UnsupportedEncodingException | MessagingException e) {
             e.printStackTrace();
         } catch (Exception e) {
             model.addAttribute("error", true);
         }

         model.addAttribute("allAuthor", allAuthor);
         model.addAttribute("allBookLang", allBookLang);
         model.addAttribute("allLanguage", allLanguage);

         return "forgot_password";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("devbooks.am@gmail.com", "Support from devbooks.am");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a></b></p>"
                + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam(value = "token", required = false) String token, Model model) {

        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();
        User user = userService.getByResetPasswordToken(token);

        model.addAttribute("allAuthor", allAuthor);
        model.addAttribute("allBookLang", allBookLang);
        model.addAttribute("allLanguage", allLanguage);
        model.addAttribute("token", token);
        model.addAttribute("user", user);

        return "reset_password";
    }

    @PostMapping("/reset_password")
    public String processResetPasswordForm(@RequestParam(value = "token", required = false) String token,
                                           @RequestParam(value = "password", required = false) String password,
                                           @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
                                           @Valid User user, BindingResult bindingResult, Model model) {

        List<Language> allLanguage = languageService.getAllLanguage();
        List<Author> allAuthor = authorService.getAllAuthor();
        List<String> allBookLang = bookService.getBookLang();

        if (bindingResult.hasFieldErrors("password")) {

             model.addAttribute("allAuthor", allAuthor);
             model.addAttribute("allBookLang", allBookLang);
             model.addAttribute("allLanguage", allLanguage);
             model.addAttribute("token", token);

             return "reset_password";
        } else if (!password.equals(confirmPassword)) {

            model.addAttribute("confirmError", true);
            model.addAttribute("allAuthor", allAuthor);
            model.addAttribute("allBookLang", allBookLang);
            model.addAttribute("allLanguage", allLanguage);
            model.addAttribute("token", token);

            return "reset_password";
        } else {

            User u = userService.getByResetPasswordToken(token);
            userService.updatePassword(u, password);

            model.addAttribute("allAuthor", allAuthor);
            model.addAttribute("allBookLang", allBookLang);
            model.addAttribute("allLanguage", allLanguage);

            return "reset_password_success";
        }
    }
}
