package es.vir2al.apuestas.fwk.domain.requests;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @AllArgsConstructor @ToString
public class NavigationInfoRequest {
    
	private int first;
	private int rows;
	private String sortField;
	private Integer sortOrder;
	private List<String> sortFieldDefault;
	private List<Integer> sortOrderDefault;

}
