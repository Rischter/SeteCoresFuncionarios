package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.setecores.funcionarios.model.Pagamento;
import com.setecores.funcionarios.model.Funcionario;
import java.time.LocalDate;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    //Lista pagamentos por funcionarios
    // Lista pagamentos por funcionário
    List<Pagamento> findByFuncionario(Funcionario funcionario);

    // Lista pagamentos em um determinado período
    List<Pagamento> findByDataPagamentoBetween(LocalDate inicio, LocalDate fim);
}
    





















