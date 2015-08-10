/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>
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

import com.abcindustries.controllers.VendorsController;
import com.abcindustries.entities.Vendor;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    @Inject
    private VendorsController vendors;

    @GET
    @Produces("application/json")
    public Response getAll() {
        // TODO: Use the controller's toJson method
        return Response.ok(vendors.toJson()).build();
        //return null;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        // TODO: Use controller's getById method to get a Product, then use toJson
        try{
            return Response.ok(vendors.getById(id).toJson()).build();
        } catch(Exception ex){
            return Response.status(500).build();
        }
    }

    @POST
    @Consumes("application/json")
    public Response add(JsonObject json) {
        // TODO: Use the controller's add method
        try {
            vendors.add(new Vendor(json));
            return Response.ok().build();
        } catch (SQLException ex) {
            Logger.getLogger(ProductsREST.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).build();
        }
    }

    //TODO: PUT /vendors/{id}    
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response set(@PathParam("id") int id, JsonObject json) {
        try {
            Vendor vendor = new Vendor(json);
            vendors.set(id, vendor);
            return Response.ok().build();
        } catch (SQLException ex) {
            return Response.status(500).build();
        }
    }
    
    //TODO: DELETE /vendors/{id}
    @DELETE
    @Consumes("application/json")
    @Path("{id}")
    public Response set(@PathParam("id") int id) {
        try {
            vendors.delete(id);
            return Response.ok().build();
        } catch (SQLException ex) {
            return Response.status(500).build();
        }
    }
}
