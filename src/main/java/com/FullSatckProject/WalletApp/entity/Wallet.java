package com.FullSatckProject.WalletApp.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(collection="wallet")
public class Wallet {
	@Id
    private Long id = new Random().nextLong();
	@NotBlank(message = "Name cann't be blank")
    @Size(min = 2,max = 30)
    private String name;
    @Size(min = 2,max = 30)
    private String accountNumber;
	@Size(max = 100)
    private String description;
    @Min(1)
    @Max(3)
    private Integer priority; //1=High; 2=Medium; 3=Low

    private int userId;

    private Double currentBalance = 0.0000;

//    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY,mappedBy = "wallet",orphanRemoval = true)

    private List<Transaction> transactions ;

//    @PrePersist
//    public void setBalance(){ this.currentBalance = new Double(0); }

}
