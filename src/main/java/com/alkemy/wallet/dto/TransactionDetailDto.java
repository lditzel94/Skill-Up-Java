package com.alkemy.wallet.dto;

import com.alkemy.wallet.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailDto{

        @JsonProperty( "id" )
        private Integer transactionID;

        @JsonProperty( "amount")
        @NotBlank( message = "Amount cannot be empty" )
        private Double amount;

        @JsonProperty( "type" )
        private TransactionType type;

        @JsonProperty( "description" )
        private String description;

        @JsonProperty( "transactionDate" )
        private Timestamp transactionDate;

}
