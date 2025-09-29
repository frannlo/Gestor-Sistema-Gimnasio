package com.gimnasio.reservas.service;

import com.gimnasio.reservas.model.Recepcionista;
import com.gimnasio.reservas.repository.RecepcionistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecepcionistaService {
    private final RecepcionistaRepository recepcionistaRepository;
    
    public List<Recepcionista> listar(){
        return recepcionistaRepository.findAll();
    }
    @Transactional
    public Recepcionista crear(Recepcionista recepcionista){
        return recepcionistaRepository.save(recepcionista);
    }
    @Transactional
    public void eliminarRecepcionista(String usuario){
        recepcionistaRepository.deleteById(usuario);
    }

    public Recepcionista login(String usuario, String pass){
        var r = recepcionistaRepository.findById(usuario).orElse(null);
        return (r != null && pass != null && pass.equals(r.getContraseña())) ? r : null;
    }
}
