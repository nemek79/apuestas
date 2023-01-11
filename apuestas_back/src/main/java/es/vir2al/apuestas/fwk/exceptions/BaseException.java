package es.vir2al.apuestas.fwk.exceptions;

import es.vir2al.apuestas.fwk.utils.constants.ResponseConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BaseException extends Exception {

    private Integer code;
	private String message;

    public BaseException() {
		
		this.code = ResponseConstants.NOT_DEFINED;
		this.message = null;
		
	}

	public BaseException(Integer code) {
		
		this.code = code;
		this.message = null;
		
	}

	public BaseException(Integer code, String message) {
		
		this.code = code;
		this.message = message;
		
	}

	public BaseException(Integer code, Exception exception) {
		
		this.code = code;
		this.message = exception.getMessage();
		
	}

}
