package com.setecores.funcionarios.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal; // Importação essencial para valores monetários

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    // --- Campos de Referência do Período (Adicionados para Histórico) ---
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    // ------------------------------------------------------------------

    private LocalDate dataPagamento; // Data em que o pagamento foi registrado/efetuado

    // --- VALORES: Alterados de Double para BigDecimal para precisão financeira ---
    private BigDecimal valorTotal = BigDecimal.ZERO; 
    private BigDecimal descontos = BigDecimal.ZERO; 
    private BigDecimal valorLiquido = BigDecimal.ZERO; 
    // -----------------------------------------------------------------------------

    @Column(columnDefinition = "TEXT")
    private String observacoes; 
}
    

