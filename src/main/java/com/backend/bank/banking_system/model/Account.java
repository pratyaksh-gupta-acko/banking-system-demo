package com.backend.bank.banking_system.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @Column(columnDefinition = "Boolean default true")
    private boolean activated=true;
    @Column
    private String name;
    @Column
    private int balance;

    @OneToMany(mappedBy = "debitedFrom")
    private List<Transaction> credit;

    @OneToMany(mappedBy = "creditedTo")
    private List<Transaction>debit;

    public Account(int id, boolean activated,String name,int balance){
        this.id=id;
        this.activated=activated;
        this.name=name;
        this.balance=balance;
    }
}
