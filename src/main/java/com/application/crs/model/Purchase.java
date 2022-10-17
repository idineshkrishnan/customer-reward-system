package com.application.crs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private long id;

    @JsonProperty(value = "descriptions")
    private String descriptions;

    @JsonProperty(value = "purchase_date")
    private LocalDate purchaseDate;

    @JsonProperty(value = "amount")
    private double amount;

    @JsonProperty(value = "customerId")
    private long customerId;
}
