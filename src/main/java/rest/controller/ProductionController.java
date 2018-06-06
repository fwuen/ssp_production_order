package rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.db.ProductionProvider;
import data.model.Production;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("production")
public class ProductionController {
    ProductionProvider productionProvider = new ProductionProvider();
    private ObjectMapper mapper = new ObjectMapper();

    /*
    @GET
    public Response getProductions()
    {
        try
        {
            List<Production> output = productionProvider.findAllProductions();
            if(output == null)
            {
                return Response.status(404).build();
            }
            return Response.status(200).entity(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output)).build();
        }
        catch (JsonProcessingException ex)
        {
            return Response.status(500).entity(ex).build();
        }
    }*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Production> getProductions() {
        return productionProvider.findAllProductions();
    }

    /*
    @GET
    @Path("/{id}")
    public Response getProductionById(@PathParam("id") int id)
    {
        try {
            Production output = productionProvider.findProductionById(id);
            if (output == null) {
                return Response.status(400).build();
            }
            return Response.status(200).entity(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output)).build();
        }
        catch (JsonProcessingException ex) {
            return Response.status(500).entity(ex).build();
        }
    }*/

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Production getProduction(@PathParam("id") String id) {
        return productionProvider.findProductionById(Integer.parseInt(id));
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduction(@PathParam("id") int id) {
        Production production = productionProvider.findProductionById(id);
        if (production == null) {
            return Response.status(404).build();
        }
        try {
            productionProvider.removeProduction(productionProvider.findProductionById(id));
            return Response.status(200).build();
        } catch (Exception ex) {
            return Response.status(500).entity(ex).build();
        }
    }

    @PUT
    public Response updateProduction(Production production) {
        try {
            productionProvider.updateProduction(production);
            return Response.status(200).entity(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(production)).build();
        } catch (Exception ex) {
            return Response.status(500).entity(ex).build();
        }
    }

    @POST
    public Response addProduction(Production production) {
        try {
            if (production == null) {
                return Response.status(400).build();
            }
            productionProvider.writeProduction(production);
            return Response.status(200).entity(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(production)).build();
        } catch (Exception ex) {
            return Response.status(500).entity(ex).build();
        }
    }
}
