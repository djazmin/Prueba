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

import co.edu.usbcali.banco.domain.Cliente;
import co.edu.usbcali.banco.domain.TipoDocumento;

class TestClienteJPA {
	long clieId=123456L;
	@Test
	@DisplayName("Crear Cliente")
	void aTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNull(cliente, "El cliente ya existe");
		
		cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(clieId);
		cliente.setDireccion("Cra 34 - 234-23");
		cliente.setNombre("Juan Perez");
		cliente.setEmail("juan@mail.com");
		cliente.setTelefono("123123");
		
		TipoDocumento tipoDoc = entityManager.find(TipoDocumento.class, 2L);
		assertNotNull(tipoDoc);
		cliente.setTipoDocumento(tipoDoc);
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Modificar Cliente")
	void bTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		cliente.setActivo("N");
		cliente.setDireccion("Cra 34 - 234-23");
		cliente.setNombre("Juan Perez Osorio");
		cliente.setEmail("juan@mail.com");
		cliente.setTelefono("123123");
		
		TipoDocumento tipoDoc = entityManager.find(TipoDocumento.class, 1L);
		assertNotNull(tipoDoc);
		cliente.setTipoDocumento(tipoDoc);
		
		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Eliminar Cliente")
	void cTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
	}
	
	private final static Logger log = LoggerFactory.getLogger(TestClienteJPA.class);
	
	@Test
	@DisplayName("Consultar Cliente")
	void dTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		String JPQL = "SELECT cli FROM Cliente cli";
		List<Cliente> losCliente = entityManager.createQuery(JPQL).getResultList();
		
		losCliente.forEach(cliente->{
			log.info("Clietnte Id: "+cliente.getClieId());
			log.info("Cliente nombre: "+cliente.getNombre());			
		});
		
		/*
		 * for (Cliente cliente : losCliente){
		   log.info("Clietnte Id: "+cliente.getClieId());
		   log.info("Cliente nombre: "+cliente.getNombre());
		  }
		 */
	}

}
