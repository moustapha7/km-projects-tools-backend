package com.km.projects.tools;

import com.km.projects.tools.model.Departement;
import com.km.projects.tools.model.ERole;
import com.km.projects.tools.model.Role;
import com.km.projects.tools.repository.DepartementRepository;
import com.km.projects.tools.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KmProjectsToolsApplication  {

	public static void main(String[] args) {
		SpringApplication.run(KmProjectsToolsApplication.class, args);
	}

	/*@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private DepartementRepository departementRepository;

	@Override
	public void run(String... args) throws Exception {

		roleRepository.save(new Role(ERole.ROLE_ADMIN));
		roleRepository.save(new Role(ERole.ROLE_USER));
		roleRepository.save(new Role(ERole.ROLE_CLIENT));

		departementRepository.save(new Departement("Service Commercial"));
        departementRepository.save(new Departement("Marketing"));
        departementRepository.save(new Departement("Informatique"));
        departementRepository.save(new Departement("Direction Financière"));
        departementRepository.save(new Departement("Ressources Humaines"));


	}

*/
}
