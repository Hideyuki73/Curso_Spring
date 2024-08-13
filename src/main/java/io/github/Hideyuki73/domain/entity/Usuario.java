package io.github.Hideyuki73.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    @NotEmpty(message = "Campo login obrigatorio")
    private String login;
    @Column
    @NotEmpty(message = "Campo password obrigatorio")
    private String senha;
    @Column
    private boolean admin;

}
