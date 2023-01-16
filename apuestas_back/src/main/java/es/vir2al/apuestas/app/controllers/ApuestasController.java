package es.vir2al.apuestas.app.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.vir2al.apuestas.app.domain.ApuestaVO;
import es.vir2al.apuestas.app.services.ApuestasService;
import es.vir2al.apuestas.fwk.domain.responses.DataResponse;
import es.vir2al.apuestas.fwk.exceptions.BaseException;
import es.vir2al.apuestas.fwk.utils.constants.ResponseConstants;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/apuestas")
public class ApuestasController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ApuestasController.class);

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private ApuestasService apuestasService;

	@PostMapping()
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> createApuesta(@Valid @RequestBody ApuestaVO iVo) {
        
        LOGGER.info("PARAMETROS {}", iVo);
        
        DataResponse<ApuestaVO> response = new DataResponse<ApuestaVO>();
        ApuestaVO data = null;

        try {
            
            data = this.apuestasService.createApuesta(iVo);
            response.setData(data);

        } catch (BaseException be) {

			LOGGER.error("{} - CODE: {}",be.getMessage(),be.getCode());
			response.setCode(be.getCode());

        } catch (Exception e) {

			LOGGER.error(e.getMessage());
			response.setCode(ResponseConstants.UNEXPECTED_ERROR);
            
        }
    
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }

}
