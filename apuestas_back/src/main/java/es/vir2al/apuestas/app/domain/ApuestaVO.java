package es.vir2al.apuestas.app.domain;

import java.io.Serializable;

import es.vir2al.apuestas.app.utils.AppUtils;
import es.vir2al.apuestas.app.utils.constants.GeneralAppConstants;
import es.vir2al.apuestas.fwk.utils.TimeUtils;
import es.vir2al.apuestas.fwk.utils.constants.GeneralConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class ApuestaVO implements Serializable {
  
    private Integer id;
    private String fechaAlta;
    private String fechaEvento;
    private TipsterVO tipster;
    private TipoApuestaVO tipoApuesta;
    private Boolean live;
    private Boolean reto;
    private DeporteVO deporte;
    private String evento;
    private String apuesta;
    private Float stake;
    private Float cuota;
    private Float cantidadApostada;
    private Float bruto;
    private EstadoApuestaVO estadoApuesta;
    private String encuentro;
    private String comentario;

    public ApuestaVO(){

        this.id = null;
        this.fechaAlta = TimeUtils.getNowString(GeneralConstants.MYSQL_DATE_FORMAT);
        this.fechaEvento = TimeUtils.getNowString(GeneralConstants.MYSQL_DATE_FORMAT);
        this.tipster = null;
        this.tipoApuesta = null;
        this.live = false;
        this.reto = false;
        this.deporte = null;
        this.evento = null;
        this.apuesta = null;
        this.stake = 0f;
        this.cuota = 0f;
        this.cantidadApostada = 0f;
        this.bruto = 0f;
        this.estadoApuesta = new EstadoApuestaVO().init();
        this.encuentro = null;
        this.comentario = null;

    }

    public Float calcularBruto() {

        this.bruto = this.cantidadApostada * this.cuota;

        return this.bruto;
    }

    public Float getNeto() {

        Float result = 0f;

        if (this.estadoApuesta == null) return 0f;

        switch (this.estadoApuesta.id) {
            case 1: // Pendiente
                
                result = (this.cantidadApostada * this.cuota) - this.cantidadApostada;

                break;

            case 2: // Ganada

                result = this.bruto - this.cantidadApostada;

                break;

            case 3: // Perdida

                result = 0f - this.cantidadApostada;

                break;

            case 4: // Push

                result = this.bruto - this.cantidadApostada;  

                break;

            default:

                result = (this.cantidadApostada * this.cuota) - this.cantidadApostada;
  
        }

        return AppUtils.round(result, GeneralAppConstants.NUM_DECIMALS);
    }

}
