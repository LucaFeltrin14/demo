package zambom.aula.demo.jogador;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends MongoRepository<Jogador, String>{
    List<Jogador> findByNome(String nome);
}
