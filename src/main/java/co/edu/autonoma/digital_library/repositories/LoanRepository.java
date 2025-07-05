/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.autonoma.digital_library.repositories;

import co.edu.autonoma.digital_library.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author candr
 */
public interface LoanRepository extends JpaRepository<Loan, Long>{
    
}
