package com.gimnasio.reservas.repository;

import com.gimnasio.reservas.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
