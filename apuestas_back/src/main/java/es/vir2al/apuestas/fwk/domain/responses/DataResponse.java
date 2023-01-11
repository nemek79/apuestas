package es.vir2al.apuestas.fwk.domain.responses;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataResponse<T> extends BaseResponse {

    protected T data;

    public DataResponse() {

        super();
        this.data = null;

    }

    public DataResponse(T data) {

        super();
        this.data = data;

    }

}
