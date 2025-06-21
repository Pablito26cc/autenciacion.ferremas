package cl.integracion.ms_auth_bs.client;

import cl.integracion.ms_auth_bs.model.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-auth-db", url = "http://localhost:8080")
public interface UsuarioDbFeignClient {
    
    @GetMapping("/usuarios")
    public List<UsuarioDTO> getAllUsuarios();
} 