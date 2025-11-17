package com.setecores.Controller; // Pacote do Controller ajustado

import com.setecores.funcionarios.model.Funcionario;
import com.setecores.funcionarios.model.Pagamento;
// Importações Corrigidas:
import   repository.FuncionarioRepository; 
import  com.Service.PagamentoService; 

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "*")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final FuncionarioRepository funcionarioRepository;

    // Injeção de Dependência
    public PagamentoController(PagamentoService pagamentoService,
                               FuncionarioRepository funcionarioRepository) {
        this.pagamentoService = pagamentoService;
        this.funcionarioRepository = funcionarioRepository;
    }

    // 1. Rota de REGISTRO MANUAL de Pagamento
    @PostMapping("/{funcionarioId}")
    public Pagamento gerarPagamento(
            @PathVariable Long funcionarioId,
            
            // Datas para documentar o período de referência (melhoria de tipagem)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            
            // Valor Total Bruto digitado pelo usuário (BigDecimal)
            @RequestParam BigDecimal valorTotal, 
            
            // Descontos (BigDecimal)
            @RequestParam(defaultValue = "0.0") BigDecimal descontos,
            
            @RequestParam(required = false) String observacoes) {

        // Tratamento de erro 404
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Funcionário ID " + funcionarioId + " não encontrado."));

        // Chamada ao Service com o valorTotal digitado (assumindo Service atualizado)
        return pagamentoService.gerarPagamento(funcionario, inicio, fim, valorTotal, descontos, observacoes);
    }

    // 2. Rota de Listagem por Funcionário
    @GetMapping("/{funcionarioId}")
    public List<Pagamento> listarPorFuncionario(@PathVariable Long funcionarioId) {
        // Tratamento de erro 404
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Funcionário ID " + funcionarioId + " não encontrado."));
        
        return pagamentoService.listarPagamentos(funcionario);
    }

    // 3. Rota de Listagem por Período de Pagamento
    @GetMapping("/periodo")
    public List<Pagamento> listarPorPeriodo(
            // Melhoria de tipagem
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        
        return pagamentoService.listarPorPeriodo(inicio, fim);
    }
}