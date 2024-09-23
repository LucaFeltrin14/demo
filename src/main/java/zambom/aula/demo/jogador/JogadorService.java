package zambom.aula.demo.jogador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zambom.aula.demo.time.Time;
import zambom.aula.demo.time.TimeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private TimeService timeService;

    public Jogador save(Jogador jogador) {
        if (jogador.getNome() == null || jogador.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do jogador não pode ser vazio");
        }

        if (jogador.getIdade() <= 15) {
            throw new IllegalArgumentException("A idade do jogador deve ser maior que 15");
        }

        return jogadorRepository.save(jogador);
    }

    public Jogador addTimeToJogador(String jogadorId, Integer time) {
        // Encontre o jogador pelo ID
        Jogador jogador = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));

        // Verifique se o Time existe
        ResponseEntity<Time> response = timeService.getTime(time);
        if (!response.getStatusCode().equals(HttpStatus.OK) ) {
            throw new IllegalArgumentException("Time não encontrado");
        }

        // Adicione o Time à lista de times do jogador
        jogador.getTimes().add(response.getBody());

        // Salve o jogador atualizado
        jogadorRepository.save(jogador);

        // Retorne o jogador atualizado
        return jogador;
    }

    public List<Jogador> getJogadores(Optional<String> jogadorId) {
        if (jogadorId.isPresent()) {
            return Collections.singletonList(jogadorRepository.findById(jogadorId.get())
                    .orElseThrow(() -> new JogadorNaoEncontradoException("Jogador não encontrado")));
        } else {
            return jogadorRepository.findAll();
        }
    }


}
