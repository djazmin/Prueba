package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.banco.domain.Cliente;
import co.edu.usbcali.banco.domain.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")

class TestClienteSpring {
	
	@PersistenceContext
	EntityManager entityManager;
	long clieId=123456L;

	@DisplayName("Crear Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	@Test
	void aTest() {
		assertNotNull(entityManager);
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
		
		entityManager.persist(cliente);		
	}
	
	
	@Test
	@DisplayName("Modificar Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	void bTest() {
		
		assertNotNull(entityManager);		
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		cliente.setActivo("N");
		cliente.setDireccion("Cra 34 - 234-23");
		cliente.setNombre("Juan Perez Osorio");
		cliente.setEmail("juan@mail.com");
		cliente.setTelefono("123123");
		entityManager.merge(cliente);
	}
	
	@Test
	@DisplayName("Eliminar Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	void cTest() {
		
		assertNotNull(entityManager);
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		entityManager.remove(cliente);
	}
	
	private final static Logger log = LoggerFactory.getLogger(TestClienteSpring.class);
	
	@Test
	@DisplayName("Consultar Cliente")
	void dTest() {
		
		assertNotNull(entityManager);
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
