package zambom.aula.demo.jogador;

import lombok.Getter;
import lombok.Setter;
import zambom.aula.demo.time.Time;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class Jogador {

    @Id
    private String id;
    private String nome;
    private Integer idade;
    private List<Time> times;



}








