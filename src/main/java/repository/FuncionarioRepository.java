package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.setecores.funcionarios.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}