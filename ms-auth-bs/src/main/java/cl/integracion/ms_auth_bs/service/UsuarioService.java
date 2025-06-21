package cl.integracion.ms_auth_bs.service;

import cl.integracion.ms_auth_bs.client.UsuarioDbFeignClient;
import cl.integracion.ms_auth_bs.model.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioDbFeignClient usuarioDbFeignClient;
    
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioDbFeignClient.getAllUsuarios();
    }
} 