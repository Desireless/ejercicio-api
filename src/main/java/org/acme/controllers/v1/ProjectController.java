package org.acme.controllers.v1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.acme.entity.Project;
import org.acme.repository.ProjectRepository;
import org.acme.services.ProjectService;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/v1/projects")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class ProjectController {

    @Inject
    AgroalDataSource agroalDataSource;

    @Inject
    ProjectService projectService;

    @GET
    public List<Project> getAllProjects(){
        List<Project> datos = projectService.findAll();
        return datos;
    }

    @GET
    @Path("/getById/{id}")
    public Response  getProjectById(Long id){
        Project project = projectService.findById(id);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .build();
        }
        return Response.ok(project).build();
    }

    @POST
    @Path("/newProject")
    public Response createProject(Project project) {
        projectService.save(project);
        return Response.status(Response.Status.CREATED).entity(project).build();
    }

    @PUT
    @Path("/updateProject/{id}")
    public Response updateProject(@PathParam("id") Long id, Project updatedProject) {
        Project existingProject = projectService.findById(id);

        if (existingProject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingProject.name = updatedProject.name;
        existingProject.startDate = updatedProject.startDate;
        existingProject.endDate = updatedProject.endDate;
        existingProject.managerId = updatedProject.managerId;

        projectService.update(existingProject);

        return Response.ok(existingProject).build();
    }

    @DELETE
    @Path("/deleteProject/{id}")
    public Response deleteProject(@PathParam("id") Long id) {
        Project existingProject = projectService.findById(id);

        if (existingProject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        projectService.deleteById(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }


    // @GET
    // @Path("/test")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String test() throws SQLException{

    //     Connection con = agroalDataSource.getConnection();

    //     Statement statement = con.createStatement();
    //     statement.execute("INSERT INTO projects (name, start_date, end_date, manager_id) VALUES ('test de conexión', '2024-01-01', '2024-05-25', 1 )");
    //     return "Probando sql";
    // }

}
