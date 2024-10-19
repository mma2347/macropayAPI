package com.mx.macropay.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "loans")
@Data
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String client;

	@Column(name = "id_loan")
	private Integer idLoan;

	@Column(name = "date_loan")
	@Temporal(TemporalType.DATE)
	private Date dateLoan;

	private BigDecimal amount;

	private String status;

	@Column(name = "id_sucursal")
	private Integer idSucursal;
}
