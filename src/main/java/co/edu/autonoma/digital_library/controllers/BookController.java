/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.autonoma.digital_library.controllers;

import co.edu.autonoma.digital_library.models.Book;
import co.edu.autonoma.digital_library.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author candr
 */
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;
    
    
    @CrossOrigin
    @GetMapping
    public List<Book> getAllBooks(){
       return this.bookRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById (@PathVariable long id){
        
        Optional<Book> book = this.bookRepository.findById(id);
        
        return book.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());  
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        Book savedBook = this.bookRepository.save(book);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }
    
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id){
        
        if(!this.bookRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        
        this.bookRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book updatedBook){
        if(!this.bookRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        
        updatedBook.setId(id);
        
        Book savedBook = this.bookRepository.save(updatedBook);
        
        return ResponseEntity.ok(savedBook);
        
    }
    
    
    
}
