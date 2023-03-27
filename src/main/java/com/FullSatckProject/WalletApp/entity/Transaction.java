package com.FullSatckProject.WalletApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction")
public class Transaction {
    @Id
    private  Long id;
    @Min(1)
    @NotBlank(message="amount can't be null")
    private Double amount;
    private String description;

    @Min(1)
    @Max(3)
    private int type;//1 for Income,2 for expenses ,3 for transfer
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date transactionDate = new Date();


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "wallet_id",nullable = false)
    private Wallet wallet;
}
