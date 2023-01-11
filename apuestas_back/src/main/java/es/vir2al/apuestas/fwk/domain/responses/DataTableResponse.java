package es.vir2al.apuestas.fwk.domain.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataTableResponse<T> extends BaseResponse {
    
	protected List<T> data;
	protected Integer total;
	
	public DataTableResponse() {
		
		super();
		this.data = new ArrayList<>();
		this.total = 0;
		
	}
    
	public DataTableResponse(List<T> data, Integer total) {
		
		super();
		this.data = data;
		this.total = total;
		
	}   

}
