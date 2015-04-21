/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abcindustries.rest;

import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@Path("vendors")
@RequestScoped
public class VendorsREST {
    // TODO: Inject the Controller
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        // TODO: Use controller's toJson method
        return null;
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        // TODO: Use controller's getById method to get a Product, then use toJson
        return null;
    }

    @POST
    @Consumes("application/json")
    public Response add(JsonObject json) {
        // TODO: Use controller's add method
        return null;
    }
    
    //TODO: PUT /vendors/{id}
    
    //TODO: DELETE /vendors/{id}
}
