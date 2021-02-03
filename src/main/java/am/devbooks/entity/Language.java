package am.devbooks.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "lang_name")
    private String langName;

    @OneToMany(targetEntity = Book.class, mappedBy = "language", fetch = FetchType.LAZY)
    List<Book> books;

    public Language() {
    }

    public Language(String langName) {
        this.langName = langName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return langName;
    }
}
