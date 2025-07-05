/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.autonoma.digital_library.controllers;

import co.edu.autonoma.digital_library.models.Book;
import co.edu.autonoma.digital_library.models.Loan;
import co.edu.autonoma.digital_library.models.User;
import co.edu.autonoma.digital_library.repositories.BookRepository;
import co.edu.autonoma.digital_library.repositories.LoanRepository;
import co.edu.autonoma.digital_library.repositories.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/loans")
public class LoanController {
    
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;
    
    
    @CrossOrigin
    @GetMapping
    public List<Loan> getAllLoans(){
       return this.loanRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById (@PathVariable long id){
        
        Optional<Loan> loan = this.loanRepository.findById(id);
        
        return loan.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());  
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Map<String, Long> body) {
        Long userId = body.get("user_id");
        Long bookId = body.get("book_id");

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user or book ID.");
        }

        Loan loan = new Loan();
        loan.setUser(userOpt.get());
        loan.setBook(bookOpt.get());
        loan.setDate(LocalDate.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(loanRepository.save(loan));
    }
    
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable long id){
        
        if(!this.loanRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        
        this.loanRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLoan(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        
        Optional<Loan> loanOpt = loanRepository.findById(id);
        if (loanOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<User> userOpt = userRepository.findById(body.get("user_id"));
        Optional<Book> bookOpt = bookRepository.findById(body.get("book_id"));

        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user or book ID.");
        }

        Loan loan = loanOpt.get();
        loan.setUser(userOpt.get());
        loan.setBook(bookOpt.get());
        loan.setDate(LocalDate.now());

        return ResponseEntity.ok(loanRepository.save(loan));
    }
    
}
