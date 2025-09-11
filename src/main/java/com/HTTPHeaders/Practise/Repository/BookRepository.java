package com.HTTPHeaders.Practise.Repository;

import com.HTTPHeaders.Practise.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}