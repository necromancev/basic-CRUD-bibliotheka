package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(max = 100, message = "Название книги не должно превышать 100 символов длиной")
    private String title;

    @Column(name = "author")
    @Size(max = 100, message = "Имя автора не должно превышать 100 символов длиной")
    @NotEmpty(message = "Поле автора не должно-быть пустым")
    private String author;

    @Column(name = "time_of_borrowment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowedAt;
    //UPDATE Book SET taken_at='2021-05-07 08:00:00' WHERE id=1;
    @Transient
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "reader_id", referencedColumnName = "reader_id")
    private Reader owner;

    @Column(name = "release_year")
    private int year;

    public Book() {
    }

    public Book(String title, String author, Date borrowedAt, int year) {
        this.title = title;
        this.author = author;
        this.borrowedAt = borrowedAt;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(Date borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isExpired(){
        return expired;
    }

    public void setExpired(boolean expired){
        this.expired = expired;
    }
}
