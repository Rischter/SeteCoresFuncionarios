package repository;

import com.setecores.funcionarios.model.Funcionario;
import com.setecores.funcionarios.model.RegistroHora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface de Repositório para a entidade RegistroHora.
 * Estende JpaRepository para operações CRUD básicas.
 */
@Repository
public interface RegistroHoraRepository extends JpaRepository<RegistroHora, Long> {

    /**
     * Busca todos os registros de hora para um funcionário específico (pelo objeto Funcionario).
     */
    List<RegistroHora> findByFuncionario(Funcionario funcionario);
    
    // --- MÉTODOS OTIMIZADOS USADOS PELO SERVICE PARA FILTRAGEM ---
    
    /**
     * Busca registros pelo ID do funcionário.
     * @param funcionarioId O ID do funcionário
     * @return Lista de registros para o funcionário.
     */
    List<RegistroHora> findByFuncionarioId(Long funcionarioId);

    /**
     * Busca registros pela data específica.
     * @param data A data do registro.
     * @return Lista de registros na data.
     */
    List<RegistroHora> findByData(LocalDate data);

    /**
     * Busca registros combinando ID do funcionário e data.
     * @param funcionarioId O ID do funcionário.
     * @param data A data do registro.
     * @return Lista de registros que atendem a ambos os critérios.
     */
    List<RegistroHora> findByFuncionarioIdAndData(Long funcionarioId, LocalDate data);
}