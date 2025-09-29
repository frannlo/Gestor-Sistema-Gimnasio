package com.gimnasio.reservas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gimnasio.reservas.Enums.TipoEntrenamiento;
import com.gimnasio.reservas.model.Instructor;
import com.gimnasio.reservas.model.Plan;
import com.gimnasio.reservas.repository.InstructorRepository;
import com.gimnasio.reservas.repository.PlanRepository;

@SpringBootApplication
public class GimnasioApplication {

	public static void main(String[] args) {
		SpringApplication.run(GimnasioApplication.class, args);
	}
	@Bean
	CommandLineRunner seed(PlanRepository planRepo, InstructorRepository instRepo) {
		return args -> {
			if (planRepo.count() == 0) {
				planRepo.save(new Plan(0, "Mensual 3 días", 20000, 3, 1));
				planRepo.save(new Plan(0, "Mensual 5 días", 22000, 5, 1));
			}
			if (instRepo.count() == 0) {
				instRepo.save(new Instructor("45939108", "Ana", "Pérez", TipoEntrenamiento.FUNCIONAL, "L-V 8-12"));
				instRepo.save(new Instructor("45111222", "Luis", "Gómez", TipoEntrenamiento.MUSCULACION, "L-V 16-20"));
			}
		};
}


}
