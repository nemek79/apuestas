package es.vir2al.apuestas.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.vir2al.apuestas.app.domain.ApuestaVO;
import es.vir2al.apuestas.app.domain.request.ApuestaRequest;
import es.vir2al.apuestas.app.services.ApuestasService;
import es.vir2al.apuestas.fwk.domain.requests.NavigationInfoRequest;
import es.vir2al.apuestas.fwk.domain.responses.BaseResponse;
import es.vir2al.apuestas.fwk.domain.responses.DataResponse;
import es.vir2al.apuestas.fwk.domain.responses.DataTableResponse;
import es.vir2al.apuestas.fwk.exceptions.BaseException;
import es.vir2al.apuestas.fwk.utils.ListUtils;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<DataResponse<ApuestaVO>> getApuestaById(@PathVariable Integer id) {

        LOGGER.debug("INICIO apuestas.getApuestaById()");

        DataResponse<ApuestaVO> response = new DataResponse<ApuestaVO>();

        try {

            response.setData(this.apuestasService.getApuestaById(id));
        
        } catch (BaseException be) {

			LOGGER.error("{} - CODE: {}", be.getMessage(), be.getCode());
			response.setCode(be.getCode());

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			response.setCode(ResponseConstants.UNEXPECTED_ERROR);

		}

        LOGGER.debug("FINAL apuestas.getApuestaById()");

        return new ResponseEntity<>(response,HttpStatus.OK);

    }

	@GetMapping()
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> getApuestas(@RequestParam Map<String, String> params, NavigationInfoRequest nav) {

        LOGGER.info("PARAMETROS {}", params);
		LOGGER.info("NAVIGATION {}", nav);

        DataTableResponse<ApuestaVO> response = new DataTableResponse<ApuestaVO>();
        List<ApuestaVO> lstData = null;
        Integer total = 0;
        ApuestaRequest criteria = new ApuestaRequest();

		RowBounds rb = (nav != null && nav.getRows() > 0) ? new RowBounds(nav.getFirst(), nav.getRows())
				: new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);

        try {
            
            criteria = this.objMapper.convertValue(params, ApuestaRequest.class);

            lstData = this.apuestasService.getApuestas(criteria, nav, rb);

            if (!ListUtils.isEmpty(lstData)) {

                total = this.apuestasService.getApuestasCount(criteria);

            }

            response.setCode(ResponseConstants.RESPONSE_OK);
            response.setData(lstData);
            response.setTotal(total);

		} catch (BaseException be) {

			LOGGER.error(be.getMessage());
			response.setCode(ResponseConstants.NOT_DEFINED);

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			response.setCode(ResponseConstants.UNEXPECTED_ERROR);

		}

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

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

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> updateApuesta(@PathVariable Integer id,@Valid @RequestBody ApuestaVO iVo) {

        LOGGER.info("PARAMETROS {} - {}",id,iVo);

        BaseResponse response = new BaseResponse();

        
        try {
            
            this.apuestasService.updateApuesta(id, iVo);

        } catch (BaseException be) {

			LOGGER.error("{} - CODE: {}",be.getMessage(),be.getCode());
			response.setCode(be.getCode());

        } catch (Exception e) {

			LOGGER.error(e.getMessage());
			response.setCode(ResponseConstants.UNEXPECTED_ERROR);
            
        }
    
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

	@PutMapping("/estado/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> updateEstadoApuesta(@PathVariable Integer id, @RequestParam Map<String, String> params) {

        LOGGER.info("PARAMETROS {}",id);

        BaseResponse response = new BaseResponse();
        Integer nuevoEstado = 0;
        
        
        try {
            
            nuevoEstado = Integer.parseUnsignedInt(params.get("estado"));

            if (nuevoEstado < 2 || nuevoEstado > 4) 
                throw new BaseException(ResponseConstants.INPUT_DATA_ERROR, "El estado a actualizar no es correcto.");



            this.apuestasService.updateEstadoApuesta(id, nuevoEstado);

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
