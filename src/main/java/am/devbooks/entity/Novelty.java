package am.devbooks.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Novelty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;

    @OneToOne
    private Book book;

    public Novelty() {
    }

    public Novelty(Date date, Book book) {
        this.date = date;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
