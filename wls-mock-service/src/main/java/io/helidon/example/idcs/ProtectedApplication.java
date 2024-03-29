package io.helidon.example.idcs;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

@LoginConfig(authMethod = "MP-JWT")
@ApplicationScoped
public class ProtectedApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(WlsMockedResource.class);
    }
}