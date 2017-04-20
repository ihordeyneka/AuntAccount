package dido.auntaccount.service.rest.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import static javax.ws.rs.core.Response.Status.OK;

public class Controller {

    protected ResponseBuilder getResponseBuilder() {
        return getResponseBuilder(OK.getStatusCode());
    }

    protected ResponseBuilder getResponseBuilder(int status) {
        ResponseBuilder builder = Response.status(status);
        setHeaders(builder);
        return builder;
    }

    private ResponseBuilder setHeaders(ResponseBuilder builder) {
        return builder.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, DELETE, PUT, OPTIONS, POST")
                .header("Access-Control-Allow-Headers", "Content-Type, x-http-method-override, If-Modified-Since, Authentication, Authorization")
                .header("Access-Control-Expose-Headers", "access-token, refresh-token, expires_in");
    }
}
