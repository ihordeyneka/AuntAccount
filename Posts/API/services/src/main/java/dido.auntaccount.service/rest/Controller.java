package dido.auntaccount.service.rest;

import javax.ws.rs.core.Response;

public class Controller {

    protected Response.ResponseBuilder getResponseBuilder() {
        return Response.ok().header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, DELETE, PUT, OPTIONS, POST")
                .header("Access-Control-Allow-Headers", "Content-Type, x-http-method-override");
    }
}
