package com.gimnasio.reservas.service;

import com.gimnasio.reservas.model.Plan;
import com.gimnasio.reservas.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public List<Plan> listarPlanes(){ return planRepository.findAll(); }
    public Plan buscarPorId(Long id) { return planRepository.findById(id).orElse(null); }

    @Transactional
    public Plan crearPlan(Plan plan) {return planRepository.save(plan);}

    @Transactional
    public Plan editarPlan(Long id, Plan cambios){
        Plan plan = planRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));
        plan.setNombre(cambios.getNombre());
        plan.setPrecio(cambios.getPrecio());
        plan.setFrecuenciaSemanal(cambios.getFrecuenciaSemanal());
        plan.setDuracionMeses(cambios.getDuracionMeses());
        return planRepository.save(plan);
    }
    @Transactional
    public void eliminarPlan(Long id) { planRepository.deleteById(id); }
}
