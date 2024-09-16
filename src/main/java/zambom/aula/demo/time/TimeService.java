package zambom.aula.demo.time;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TimeService {

    public ResponseEntity<Time> getTime(Integer idTime) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                "http://campeonato:8080/time/" + idTime,
                Time.class);
    }

}
