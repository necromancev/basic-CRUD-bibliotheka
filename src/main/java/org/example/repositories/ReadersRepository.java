package org.example.repositories;

import org.example.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadersRepository extends JpaRepository<Reader, Integer> {

}
