package com.example.demo.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Cities")
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String name;

    @Min(value = 0, message = " phải là số dương")
    private Float area;

    @Min(value = 0, message = " phải là số dương")
    private Float population;

    @Min(value = 0, message = " phải là số dương")
    private Float  gdp;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @Column(nullable=false, columnDefinition="TEXT")
    private String introduce;

}
