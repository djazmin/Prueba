package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.domain.TipoDocumento;

class TestTipoDocumentoJPA {

	long tdocId = 4L;
	@DisplayName("Crear Tipo Documento")
	@Test
	void aTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		TipoDocumento tipoDoc = entityManager.find(TipoDocumento.class, tdocId);
		assertNull(tipoDoc, "El tipo de documento ya existe");
		
		tipoDoc = new TipoDocumento();
		tipoDoc.setActivo("S");
		tipoDoc.setNombre("Tipo Prueba");
		tipoDoc.setTdocId(tdocId);
		entityManager.getTransaction().begin();
		entityManager.persist(tipoDoc);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Modificar Tipo Documento")
	void bTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		TipoDocumento tipoDoc = entityManager.find(TipoDocumento.class,tdocId );
		assertNotNull(tipoDoc, "El cliente no existe");
		
		tipoDoc.setActivo("N");
		tipoDoc.setNombre("HAbanna");
		tipoDoc.setTdocId(tdocId);
		
		entityManager.getTransaction().begin();
		entityManager.merge(tipoDoc);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Eliminar Tipo Documento")
	void cTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		TipoDocumento tipoDoc = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDoc, "El Tipo de Documento no existe");
		
		entityManager.getTransaction().begin();
		entityManager.remove(tipoDoc);
		entityManager.getTransaction().commit();
	}
	
	private final static Logger log = LoggerFactory.getLogger(TestClienteJPA.class);
	
	@Test
	@DisplayName("Consultar Tipo Documento")
	void dTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		String JPQL = "SELECT tipoDoc FROM TipoDocumento tipoDoc";
		List<TipoDocumento> losTiposDoc = entityManager.createQuery(JPQL).getResultList();
		
		losTiposDoc.forEach(tipoDoc->{
			log.info("Nombre tipo documento: "+tipoDoc.getNombre());
			log.info("Tipo documento id: "+tipoDoc.getTdocId());			
		});
		
		/*
		 * for (Cliente cliente : losCliente){
		   log.info("Clietnte Id: "+cliente.getClieId());
		   log.info("Cliente nombre: "+cliente.getNombre());
		  }
		 */
	}


}
