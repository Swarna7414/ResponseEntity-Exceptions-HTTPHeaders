package com.HTTPHeaders.Practise.web;

import com.HTTPHeaders.Practise.Model.Book;
import com.HTTPHeaders.Practise.Repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Book>> all(@RequestHeader HttpHeaders requestHeaders){
        String userAgent = requestHeaders.getFirst(HttpHeaders.USER_AGENT);
        String host  = requestHeaders.getFirst(HttpHeaders.HOST);

        List<MediaType> accept = requestHeaders.getAccept();

        HttpHeaders response = new HttpHeaders();

        response.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCacheControl(CacheControl.noCache().getHeaderValue());
        response.add("X-Request-Id", UUID.randomUUID().toString());

        response.add(HttpHeaders.SET_COOKIE, "VISITOR="+ (userAgent != null ? userAgent.hashCode() : 0) + "; Path=/; HttpOnly");

        return new ResponseEntity<>(bookRepository.findAll(), response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id, @RequestHeader(value = HttpHeaders.ACCEPT, required = false) String accept){
        return bookRepository.findById(id).map(book -> {
            HttpHeaders response = new HttpHeaders();
            response.setContentType(MediaType.APPLICATION_JSON);
            response.setCacheControl("max-age=60");
            return new ResponseEntity<>(book, response, HttpStatus.OK);
        }).orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> create(@Valid @RequestBody Book book, @RequestHeader(value = HttpHeaders.CONTENT_TYPE) String contnetType,
                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader){

        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contnetType)){
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }

        Book saved = bookRepository.save(book);


        HttpHeaders response = new HttpHeaders();

        response.setContentType(MediaType.APPLICATION_JSON);
        response.setLocation(URI.create("/api/books/" + saved.getId()));
        response.add("X-Auth-Used",authHeader !=null ? "true" : "false");
        return new ResponseEntity<>(saved, response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody Book book, @RequestHeader HttpHeaders httpHeaders){
        String client = httpHeaders.getFirst(httpHeaders.USER_AGENT);


        return bookRepository.findById(id).map(existing->{
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            Book saved = bookRepository.save(existing);

            HttpHeaders response = new HttpHeaders();
            response.setContentType(MediaType.APPLICATION_JSON);
            response.add("X-Updated-By",client !=null ? client : "UnKnown");

            return new ResponseEntity<>(saved, response, HttpStatus.OK);
        }).orElseGet(()-> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader(value = "X-API-KEY", required = false) String apikey){
        if (!bookRepository.existsById(id)) return ResponseEntity.notFound().build();

        bookRepository.deleteById(id);

        HttpHeaders response = new HttpHeaders();

        response.add("X-API-KEY-Used", apikey != null ? "true" : "false'");
        response.setCacheControl(CacheControl.noStore().getHeaderValue());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}