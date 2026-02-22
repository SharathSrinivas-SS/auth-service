package com.example.auth.resource;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.service.JwtService;
import com.example.auth.service.PasswordService;
import com.example.auth.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(AuthResource.HOME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    public static final String HOME= "/auth";
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";

    @Inject
    UserService userService;

    @Inject
    PasswordService passwordService;

    @Inject
    JwtService jwtService;

    @POST
    @Path(REGISTER)
    public Response register(RegisterRequest request){
        if(userService.existByEmail(request.email)){
            return Response.status(Response.Status.CONFLICT)
                    .entity("User already exists")
                    .build();
        }
        String hashedPassword = passwordService.hash(request.password);
        userService.createUser(request.email, hashedPassword);

        return Response.status(Response.Status.CREATED)
                .entity("user registered successfully")
                .build();
    }

    @POST
    @Path(LOGIN)
    public Response login(LoginRequest request) {
        var user = userService.findByEmail(request.email);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid Credentials")
                    .build();
        }

        boolean passwordMatches = passwordService.matches(request.password, user.password);

        if (!passwordMatches) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid Credentials")
                    .build();
        }

        String token = jwtService.generateToken(user.id.toString(), user.email);

        return Response.ok()
                .entity(token)
                .build();
    }
}
