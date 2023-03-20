package com.renan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER")
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable = false)
    @NotBlank
    @Size(max = 256)
    private String firstName;

    @Column(name="last_name", nullable = false)
    @NotBlank
    @Size(max = 256)
    private String lastName;

    @Column(name="age", nullable = false)
    @Min(1)
    @Max(200)
    private Integer age;
}
