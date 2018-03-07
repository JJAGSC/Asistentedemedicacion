/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medicacion.asistente.rest;

import com.google.gson.Gson;
import com.medicacion.asistente.bd.MedicamentoBD;
import com.medicacion.asistente.pojos.Medicamento;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Juanjo
 */
@Path("medicamentos")
public class MedicamentosResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MedicamentosResource
     */
    public MedicamentosResource() {
    }

    /**
     * Retrieves representation of an instance of com.medicacion.asistente.MedicamentosResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("pruebaconexion")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        return "Conectado";
    }
    
    /**
     * Retrieves representation of an instance of com.medicacion.asistente.MedicamentosResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMedicamentos() {
        
        List<Medicamento> medicamentos;
        try {
            medicamentos = MedicamentoBD.getAllMedicamentos();
            
            String json = new Gson().toJson(medicamentos);
        
            return Response.ok(json,MediaType.APPLICATION_JSON).build();
            
        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER).entity("Error al consultar la tabla de datos").build();
        }
    }

    // Añadir registro
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response addMedicamento(Medicamento registro){
        
        try{
            MedicamentoBD.addMedicamento(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString()+": Se ha añadido - addMedicamento "+registro.getPK_ID());
            String json = "{ \"id\": \"" + String.valueOf(registro.getPK_ID())+"\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        
        } catch (Exception e){
            return Response.status(Response.Status.SEE_OTHER).entity("Error al insertar registro: "+registro.getPK_ID()).build();
        }
    }
    
    // Actualizar registro
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMedicamento(Medicamento registro, @PathParam("id") int id) {
        System.out.println("Llegó a la actualización");
        
        try{
            MedicamentoBD.updateMedicamento(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString()+": Se ha actualizado - updateMedicamento "+registro.getPK_ID());
            String json = "{ \"id\": \"" + String.valueOf(registro.getPK_ID())+"\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        
        } catch (Exception e){
            return Response.status(Response.Status.SEE_OTHER).entity("Error al insertar registro: "+registro.getPK_ID()).build();
        }
    }
    
    // Borrar registro
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delMedicamento(@PathParam("id") int id){
        try{
            MedicamentoBD.delMedicamento(id);
            Date fecha = new Date();
            System.out.println(fecha.toString()+": Se ha borrado - delMedicamento "+id);
            String json = "{ \"id\": \"" +id+"\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        
        } catch (Exception e){
            return Response.status(Response.Status.SEE_OTHER).entity("Error al borrar registro: "+id).build();
        }
    }
}
