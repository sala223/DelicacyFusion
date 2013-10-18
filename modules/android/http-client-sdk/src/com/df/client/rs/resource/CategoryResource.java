package com.df.client.rs.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import com.df.masterdata.entity.Category;

@Path("/{tenantId}/category")
public interface CategoryResource extends Resource {

    @GET
    @Produces("multipart/mixed")
    @PartType("application/json")
    public List<Category> getCategories(@PathParam("tenantId") String tenantId);
}
