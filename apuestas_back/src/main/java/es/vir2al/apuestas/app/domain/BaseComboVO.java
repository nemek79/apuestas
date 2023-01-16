package es.vir2al.apuestas.app.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
public class BaseComboVO implements Serializable {
    
    protected   Integer id;
    protected   String descripcion;
    protected   String comentario;

}
