package com.mx.macropay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.macropay.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
