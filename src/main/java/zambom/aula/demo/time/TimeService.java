package zambom.aula.demo.time;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TimeService {

    public ResponseEntity<Time> getTime(Integer idTime) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                "http://localhost:8080/time/" + idTime,
                Time.class);
    }

}
