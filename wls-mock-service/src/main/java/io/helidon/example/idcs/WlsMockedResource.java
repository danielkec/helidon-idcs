package io.helidon.example.idcs;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/wls-service")
@ApplicationScoped
public class WlsMockedResource {

    @Inject
    private JsonWebToken jwt;

    @GET
    @RolesAllowed({"secret_role"})
    public JsonObject signal(@Context SecurityContext securityContext) {
        String user = securityContext.getUserPrincipal().getName();
        JsonObjectBuilder userObject = Json.createObjectBuilder();
        userObject.add("name", user);

        return Json.createObjectBuilder()
                .add("user", userObject)
                .build();
    }
}