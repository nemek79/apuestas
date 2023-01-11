package es.vir2al.apuestas.fwk.domain.responses;

import java.io.Serializable;

import es.vir2al.apuestas.fwk.utils.TimeUtils;
import es.vir2al.apuestas.fwk.utils.constants.GeneralConstants;
import es.vir2al.apuestas.fwk.utils.constants.ResponseConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BaseResponse implements Serializable {
   
    protected String timestamp;
	protected Integer code;

    public BaseResponse() {

        this.code = ResponseConstants.RESPONSE_OK;
        this.timestamp = TimeUtils.getNowString(GeneralConstants.TIMESTAMP_CHAR_RESPONSE);

    }

}
