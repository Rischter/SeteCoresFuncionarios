package com.setecores.funcionarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


private long Id;

private String nome;

private String cargo;

private Double valorHora; // valor que o funcionario ganha por hora

}