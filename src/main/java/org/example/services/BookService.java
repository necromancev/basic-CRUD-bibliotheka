package org.example.services;

import org.example.models.Book;
import org.example.models.Reader;
import org.example.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sortByYear){
        if(sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if(sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> searchByTitle(String query){
        return booksRepository.findAllByTitleStartingWith(query);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book bookToBeUpdated){
        bookToBeUpdated.setId(id);
        booksRepository.save(bookToBeUpdated);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id){
        booksRepository.findById(id).ifPresent(book -> {
        book.setOwner(null);
        book.setBorrowedAt(null);
        });
    }

    @Transactional
    public void assign(int id, Reader selectedReader){
        Book selectedBook = findOne(id);
        selectedBook.setOwner(selectedReader);
        selectedBook.setBorrowedAt(new Date());
        booksRepository.save(selectedBook);
    }

    public Reader getBookOwner(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

}
