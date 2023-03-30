package com.FullSatckProject.WalletApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Random;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction")
public class Transaction {
    @Id
    private  Long id  = new Random().nextLong();;
    @Min(1)
    @NotNull(message="amount can't be null")
    private Double amount;
    private String description;

    private String userEmail;
    @Min(1)
    @Max(3)
    private int type;//1 for Income,2 for expenses ,3 for transfer
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date transactionDate = new Date();


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "wallet_id",nullable = false)

    private Long walletId;
}
