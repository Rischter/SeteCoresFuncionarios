package com.Service;

import com.setecores.funcionarios.model.Funcionario;
import com.setecores.funcionarios.model.RegistroHora;

import repository.RegistroHoraRepository; // Mantido o pacote original para compatibilidade

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de Serviço para Regras de Negócio e persistência de RegistroHora.
 */
@Service
public class RegistroHoraService {

    private final RegistroHoraRepository registroHoraRepository;

    @Autowired
    public RegistroHoraService(RegistroHoraRepository registroHoraRepository) {
        this.registroHoraRepository = registroHoraRepository;
    }

    // Método 1: Salva ou Atualiza o Registro de Hora
    public RegistroHora salvarRegistro(RegistroHora registro) {
        return registroHoraRepository.save(registro);
    }

    // Método 2: Exclui um Registro de Hora
    public void excluirRegistro(Long id) {
        if (!registroHoraRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Hora não encontrado para exclusão.");
        }
        registroHoraRepository.deleteById(id);
    }

    /**
     * MÉTODO CORRIGIDO E OTIMIZADO:
     * Busca Registros usando filtros, delegando a consulta ao banco de dados (Repository).
     *
     * @param funcionarioId ID do funcionário para filtro (opcional)
     * @param data Data específica para filtro (opcional)
     * @return Lista de registros de horas filtrados de forma eficiente.
     */
    public List<RegistroHora> buscarRegistrosComFiltros(Long funcionarioId, LocalDate data) {
        if (funcionarioId != null && data != null) {
            // Busca por Funcionário e Data (REQUER MÉTODO findByFuncionarioIdAndData NO REPOSITORY)
            return registroHoraRepository.findByFuncionarioIdAndData(funcionarioId, data);
        } else if (funcionarioId != null) {
            // Busca apenas por Funcionário (REQUER MÉTODO findByFuncionarioId NO REPOSITORY)
            return registroHoraRepository.findByFuncionarioId(funcionarioId);
        } else if (data != null) {
            // Busca apenas por Data (REQUER MÉTODO findByData NO REPOSITORY)
            return registroHoraRepository.findByData(data);
        } else {
            // Sem filtros: retorna todos
            return registroHoraRepository.findAll();
        }
    }
    
    // Método 4: Simulação de listagem por componentes de data (mantido da sua versão)
    public List<RegistroHora> listarPorComponentesData(Funcionario funcionario, Integer dia, Integer mes, Integer ano) {
        
        List<RegistroHora> todosRegistros = registroHoraRepository.findByFuncionario(funcionario);

        return todosRegistros.stream()
                .filter(r -> {
                    LocalDate data = r.getData();
                    boolean match = true;
                    if (dia != null) match = match && data.getDayOfMonth() == dia;
                    if (mes != null) match = match && data.getMonthValue() == mes;
                    if (ano != null) match = match && data.getYear() == ano;
                    return match;
                })
                .collect(Collectors.toList());
    }

    public List<RegistroHora> listarPorFuncionario(Funcionario funcionario) {
        return registroHoraRepository.findByFuncionario(funcionario);
    }

    public List<RegistroHora> listarPorPeriodo(Funcionario funcionario, LocalDate inicio, LocalDate fim) {
        // Implementação simplificada (o ideal seria usar findByFuncionarioAndDataBetween no Repository)
        return registroHoraRepository.findByFuncionario(funcionario).stream()
                .filter(r -> r.getData().isAfter(inicio.minusDays(1)) && r.getData().isBefore(fim.plusDays(1)))
                .collect(Collectors.toList());
    }
}