package org.acme.services;

import java.util.List;
import org.acme.entity.Project;
import org.acme.repository.ProjectRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ProjectService {

    @Inject
    ProjectRepository projectRepository;

    public Project save(Project project) {
        projectRepository.persist(project);
        return project;
    }

    public Project update(Project project) {
        // projectRepository.update(project);
        EntityManager em = projectRepository.getEntityManager();
        em.merge(project);
        return project;
    }

    public List<Project> findAll() {
        return projectRepository.listAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id);
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}