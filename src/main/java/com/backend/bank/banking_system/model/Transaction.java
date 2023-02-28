package com.backend.bank.banking_system.model;

import com.backend.bank.banking_system.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name="debited_from")
    private Account debitedFrom;
    @ManyToOne
    @JoinColumn(name="credited_to")
    private Account creditedTo;
    @Column
    private int amount;
    @Column
    private TransactionType type;

    public Transaction(Account debitedFrom, Account creditedTo, int amount, TransactionType type){
        this.debitedFrom=debitedFrom;
        this.creditedTo=creditedTo;
        this.amount=amount;
        this.type=type;
    }

    public Transaction(int id, Timestamp timestamp, Account account, int amount, TransactionType type){
        this.id = id;
        this.timestamp = timestamp;
        if(type == TransactionType.DEBIT)
            this.debitedFrom = account;
        if(type == TransactionType.CREDIT)
            this.creditedTo = account;
        this.amount=amount;
        this.type=type;
    }
    public Transaction(Account account, int amount, TransactionType type){
        if(type == TransactionType.DEBIT)
            this.debitedFrom = account;
        if(type == TransactionType.CREDIT)
            this.creditedTo = account;
        this.amount=amount;
        this.type=type;
    }

    public Transaction(int id, int amount, TransactionType type){
        this.id = id;
        this.amount = amount;
        this.type = type;
    }
}