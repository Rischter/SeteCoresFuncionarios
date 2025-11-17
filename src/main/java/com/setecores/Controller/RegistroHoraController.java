package com.setecores.Controller;

import com.Service.RegistroHoraService; // Ajustei o pacote para 'com.setecores.Service'
import com.setecores.funcionarios.model.Funcionario;
import com.setecores.funcionarios.model.RegistroHora;
import   repository.FuncionarioRepository; // Pacote ajustado para 'com.setecores.Repository'
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/registroHoras") // Ajustado para /registroHoras para ser mais claro
@CrossOrigin(origins = "*") // Mantendo CORS liberado
public class RegistroHoraController {

    private final RegistroHoraService registroHoraService;
    private final FuncionarioRepository funcionarioRepository;

    public RegistroHoraController(RegistroHoraService registroHoraService,
                                  FuncionarioRepository funcionarioRepository) {
        this.registroHoraService = registroHoraService;
        this.funcionarioRepository = funcionarioRepository;
    }

    // --- 1. POST: Registrar/Atualizar Marcações de Ponto ---
    @PostMapping("/{funcionarioId}")
    public RegistroHora registrarHora(@PathVariable Long funcionarioId, @RequestBody RegistroHora registro) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado."));
        
        registro.setFuncionario(funcionario);
        // Garante que a data do registro seja a atual, se não for fornecida no body
        if (registro.getData() == null) {
            registro.setData(LocalDate.now());
        }
        return registroHoraService.salvarRegistro(registro);
    }

    // --- 2. GET: Listar Histórico com Filtros (Novo Endpoint para historicoHoras.html) ---
    /**
     * Lista todos os registros de horas, com filtros opcionais por funcionário e/ou data.
     * Mapeado para a URL: GET /registroHoras?funcionarioId={id}&data={yyyy-MM-dd}
     */
    @GetMapping
    public List<RegistroHora> listarComFiltros(
            @RequestParam(required = false) Long funcionarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        // O serviço tratará a lógica de busca/filtragem
        return registroHoraService.buscarRegistrosComFiltros(funcionarioId, data);
    }

    // --- 3. DELETE: Excluir um Registro de Hora (Novo Endpoint para historicoHoras.html) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirRegistro(@PathVariable Long id) {
        registroHoraService.excluirRegistro(id);
        // Retorna 204 No Content se a exclusão for bem-sucedida (ou se o service lançar exceção 404)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
    
    // --- MÉTODOS EXISTENTES ADAPTADOS ---
    
    // O endpoint listarPorFuncionario (GET /{funcionarioId}) foi movido para o GET principal com filtro.
    // O endpoint listarPorPeriodo (GET /{funcionarioId}/periodo) foi removido em favor do método mais flexível.
    
    // --- 4. GET: Filtragem Flexível por Dia/Mês/Ano (Rota ajustada) ---
    @GetMapping("/relatorio/{funcionarioId}")
    public List<RegistroHora> filtrarPorData(
            @PathVariable Long funcionarioId,
            @RequestParam(required = false) Integer dia,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado."));

        // Chama o Service, que será responsável por montar o período de busca
        return registroHoraService.listarPorComponentesData(funcionario, dia, mes, ano);
    }
}