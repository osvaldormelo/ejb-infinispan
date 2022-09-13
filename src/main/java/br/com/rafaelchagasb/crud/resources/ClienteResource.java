package br.com.rafaelchagasb.crud.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import br.com.rafaelchagasb.crud.ejb.CacheChaveMemoriaEjb;
import br.com.rafaelchagasb.crud.utils.ModelMapperUtil;

@Path("cliente")
public class ClienteResource {
	
	private static ModelMapperUtil mapper = new ModelMapperUtil();
	
//	@Inject
//	ClienteEJB service;
	
	@Inject
	CacheChaveMemoriaEjb cacheChaveMemoriaEjb;
	
	@GET
	public String cache(@QueryParam("valor") String valor) {
		
		cacheChaveMemoriaEjb.get(valor);
		
		return "cache ejb";
	}

}
