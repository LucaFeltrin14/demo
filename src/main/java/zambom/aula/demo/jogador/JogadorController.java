package zambom.aula.demo.jogador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogador")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    @GetMapping
    public ResponseEntity<List<Jogador>> getJogadores(@RequestParam Optional<String> jogadorId) {
        return ResponseEntity.ok(jogadorService.getJogadores(jogadorId));
    }

}