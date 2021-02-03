package am.devbooks.entity;

import javax.persistence.*;

@Entity
public class Popular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int visit;

    @OneToOne(mappedBy = "popular", targetEntity = Book.class)
    private Book book;

    public Popular() {
    }

    public Popular(int visit, Book book) {
        this.visit = visit;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
