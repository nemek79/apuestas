package es.vir2al.apuestas.app.domain.request;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor @ToString
public class ApuestaRequest implements Serializable {
 
    private Integer id;
    private Integer tipsterId;
    private Integer deporteId;
    private Integer estadoId;
    private Integer tipoId;
    private Boolean reto;
    private Boolean live;
    private String fechaIni;
    private String fechaFin;
    private Integer stake;
    private String evento;

}
