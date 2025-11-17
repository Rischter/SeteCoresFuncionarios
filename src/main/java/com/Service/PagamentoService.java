package com.Service;

import com.setecores.funcionarios.model.Funcionario;
import com.setecores.funcionarios.model.Pagamento;
import repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List; // Importação necessária

// Ajuste Conceitual:
@Service
public class PagamentoService {
    
    private final PagamentoRepository pagamentoRepository;
    
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    // A função gerarPagamento (já fornecida) é mantida aqui para contexto...
    public Pagamento gerarPagamento(Funcionario funcionario, 
                                    LocalDate inicio, 
                                    LocalDate fim, 
                                    BigDecimal valorTotal, 
                                    BigDecimal descontos, 
                                    String observacoes) {

        BigDecimal valorLiquido = valorTotal.subtract(descontos); 

        Pagamento pagamento = new Pagamento();
        pagamento.setFuncionario(funcionario);
        pagamento.setPeriodoInicio(inicio); 
        pagamento.setPeriodoFim(fim);
        pagamento.setDataPagamento(LocalDate.now());
        pagamento.setValorTotal(valorTotal); 
        pagamento.setDescontos(descontos);
        pagamento.setValorLiquido(valorLiquido);
        pagamento.setObservacoes(observacoes);

        // Certifique-se de que o valorLiquido tem duas casas decimais, como prática
        pagamento.setValorLiquido(valorLiquido.setScale(2, java.math.RoundingMode.HALF_UP));

        return pagamentoRepository.save(pagamento);
    }

    // --- MÉTODOS FALTANTES ---
    
    /**
     * Lista todos os pagamentos de um funcionário específico.
     * Requer que o PagamentoRepository tenha o método findByFuncionario(Funcionario funcionario).
     */
    public List<Pagamento> listarPagamentos(Funcionario funcionario) {
        return pagamentoRepository.findByFuncionario(funcionario);
    }

    /**
     * Lista todos os pagamentos registrados entre duas datas (dataPagamento).
     * Requer que o PagamentoRepository tenha o método findByDataPagamentoBetween(LocalDate inicio, LocalDate fim).
     */
    public List<Pagamento> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return pagamentoRepository.findByDataPagamentoBetween(inicio, fim);
    }
}