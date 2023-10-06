package org.example.services;

import org.example.models.Book;
import org.example.models.Reader;
import org.example.repositories.ReadersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReaderService {

    private final ReadersRepository readersRepository;

    @Autowired
    public ReaderService(ReadersRepository readersRepository) {
        this.readersRepository = readersRepository;
    }

    public List<Reader> findAll(){
        return readersRepository.findAll();
    }

    public Reader findOne(int id){
        Optional<Reader> foundReader = readersRepository.findById(id);
        return foundReader.orElse(null);
    }

    @Transactional
    public void save(Reader reader){
        readersRepository.save(reader);
    }

    @Transactional
    public void update(int id, Reader readerToBeUpdated){
        readerToBeUpdated.setId(id); //указали айди, чтобы реп понимал, что он уже есть в табле и надо обновить значения остальные
        readersRepository.save(readerToBeUpdated);
    }

    @Transactional
    public void delete(int id){
        readersRepository.deleteById(id);
    }

    public List<Book> getBooksByReaderId(int id){
        Optional<Reader> reader = readersRepository.findById(id);

        if(reader.isPresent()){
            Hibernate.initialize(reader.get().getOwnedBooks());

            reader.get().getOwnedBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getBorrowedAt().getTime() - new Date().getTime());
                if(diffInMillies > 864000000) book.setExpired(true); //10 суток
            });
            return reader.get().getOwnedBooks();
        }
        else {
            return Collections.emptyList();
        }
    }

}
