package twello.resources;

import io.dropwizard.hibernate.UnitOfWork;
import twello.models.Workspace;
import twello.models.WorkspaceDAO;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
public class WorkspacesResource {
    private final WorkspaceDAO dao;

    public WorkspacesResource(WorkspaceDAO dao) {
        this.dao = dao;
    }

    @POST
    @Path("new")
    @UnitOfWork
    public Object createWorkspace(LinkedHashMap<String, Object> json) {
        Workspace ws = new Workspace(UUID.randomUUID());
        dao.save(ws);
        return ws;
    }

    @GET
    @Path("{identifier}")
    @UnitOfWork
    public Workspace getWorkspace(@PathParam("identifier") String identifier) {
        // FIXME check for "bad request" on identifier
        try {
            return dao.findByIdentifier(UUID.fromString(identifier));
        }
        catch (NoResultException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

}
