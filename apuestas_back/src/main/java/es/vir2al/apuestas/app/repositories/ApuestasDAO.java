package es.vir2al.apuestas.app.repositories;

import org.apache.ibatis.annotations.Param;

import es.vir2al.apuestas.app.domain.ApuestaVO;

public interface ApuestasDAO {
    
    public Integer createApuesta(
        @Param("criteria") ApuestaVO data
    );

}
