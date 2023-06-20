package io.helidon.example.idcs;

import io.helidon.security.SecurityContext;
import io.helidon.security.annotations.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;


@Path("/helidon")
@ApplicationScoped
@Authenticated
public class HelidonResource {

    @Inject
    @ConfigProperty(name = "wls.service.url")
    private URI wlsServiceUri;

    @Inject
    private JsonWebToken jwt;

    @Authenticated
    @GET
    @RolesAllowed({"secret_role"})
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getDefaultMessage(@Context SecurityContext secCtx) {
        var user = secCtx.userName();
        var isInRole = secCtx.isUserInRole("secret_role");
        var bearerToken = jwt.getRawToken(); // Manually access raw bearer token

        // Bearer token is propagated automatically no manual action is needed with JAX-RS client
        JsonObject response = ClientBuilder.newClient()
                .target(wlsServiceUri)
                .request()
                .buildGet()
                .invoke(JsonObject.class);

        return Json.createObjectBuilder()
                .add("user", user)
                .add("is_secret_role", isInRole)
                .add("wls-response", response)
                .build();
    }
}
