package es.vir2al.apuestas.app.domain.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor @ToString
public class ApuestaRequest implements Serializable {
 
    private Integer id;
    private Integer tipsterId;

}
