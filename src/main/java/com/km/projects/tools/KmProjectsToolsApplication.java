package com.km.projects.tools;

import com.km.projects.tools.model.Departement;
import com.km.projects.tools.model.ERole;
import com.km.projects.tools.model.Role;
import com.km.projects.tools.model.StatusProject;
import com.km.projects.tools.repository.DepartementRepository;
import com.km.projects.tools.repository.RoleRepository;
import com.km.projects.tools.repository.StatusProjectRepository;
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
	private DepartementRepository  departementRepository;


	@Autowired
	private StatusProjectRepository statusProjectRepository;

	@Override
	public void run(String... args) throws Exception {

		roleRepository.save(new Role(ERole.ROLE_ADMIN));
		roleRepository.save(new Role(ERole.ROLE_USER));
		roleRepository.save(new Role(ERole.ROLE_DEV));
		roleRepository.save(new Role(ERole.ROLE_POWNER));
		roleRepository.save(new Role(ERole.ROLE_TEACHLEAD));

		departementRepository.save(new Departement("Service Commercial"));
        departementRepository.save(new Departement("Marketing"));
        departementRepository.save(new Departement("Informatique"));
        departementRepository.save(new Departement("Direction Financière"));
        departementRepository.save(new Departement("Ressources Humaines"));

        statusProjectRepository.save(new StatusProject("ONGOING"));
		statusProjectRepository.save(new StatusProject("FINISHED"));
		statusProjectRepository.save(new StatusProject("SUSPENDED"));
		statusProjectRepository.save(new StatusProject("CANCELLED"));


	}
*/
}
