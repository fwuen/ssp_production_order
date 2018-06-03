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
    }

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
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteProduction(@PathParam("id") int id) {
        productionProvider.removeProduction(productionProvider.findProductionById(id));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Production updateProduction(Production production) {
        productionProvider.updateProduction(production);
        return production;
    }

    @POST
    public Production addProduction(Production production) {
        productionProvider.writeProduction(production);
        return production;
    }
}
