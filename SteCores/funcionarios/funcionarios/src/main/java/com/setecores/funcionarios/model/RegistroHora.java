package com.setecores.funcionarios.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "registro_hora") // 👈 Corrige o nome da tabela (evita o erro "resgistro_hora")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(nullable = false)
    private LocalDate data; // Data do registro

    private LocalTime entrada;    // Entrada do expediente
    private LocalTime intervalo;  // Saída para o intervalo
    private LocalTime retorno;    // Retorno do intervalo
    private LocalTime saida;      // Saída do expediente

    private Double horaExtra;     // Total de horas extras no dia

    @Column(columnDefinition = "TEXT")
    private String observacoes;   // Anotações do dia (ex: atraso, feriado etc.)
}















