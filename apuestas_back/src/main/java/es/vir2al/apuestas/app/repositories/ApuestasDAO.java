package es.vir2al.apuestas.app.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import es.vir2al.apuestas.app.domain.ApuestaVO;
import es.vir2al.apuestas.app.domain.request.ApuestaRequest;
import es.vir2al.apuestas.fwk.domain.requests.NavigationInfoRequest;

public interface ApuestasDAO {
    
    public Integer createApuesta(
        @Param("criteria") ApuestaVO data
    );

    public ApuestaVO getApuestaById(
            @Param("id") Integer id
        );

    public List<ApuestaVO> getApuestas(
            @Param("criteria") ApuestaRequest criteria, 
            @Param("navigation") NavigationInfoRequest navigation, 
            @Param("rb") RowBounds rb
        );

    public Integer getApuestasCount(
            @Param("criteria") ApuestaRequest criteria
        );

    public Integer updateApuesta(
            @Param("id") Integer id,
            @Param("criteria") ApuestaVO data
        );
}
