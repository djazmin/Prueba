package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
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

import co.edu.usbcali.banco.domain.TipoUsuario;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
class TestTipoUsuarioSpring {

	@PersistenceContext
	EntityManager entityManager;
	long tiusId=5L;
	
	@DisplayName("Crear Tipo de Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	@Test
	void aTest() {
		assertNotNull(entityManager);
		TipoUsuario tipoUsu = entityManager.find(TipoUsuario.class, tiusId);
		assertNull(tipoUsu, "El tipo de usuario ya existe");
		
		tipoUsu = new TipoUsuario();
		tipoUsu.setActivo("S");
		tipoUsu.setNombre("Tipo Prueba");
		tipoUsu.setTiusId(tiusId);
		
		entityManager.persist(tipoUsu);
	}
	
	@Test
	@DisplayName("Modificar Tipo Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	void bTest() {
		
		assertNotNull(entityManager);
		TipoUsuario tipoUsu = entityManager.find(TipoUsuario.class, tiusId);
		assertNotNull(tipoUsu, "El tipo de documento no existe");
		tipoUsu.setActivo("N");
		tipoUsu.setNombre("Tipo prueba modificado");
		
		entityManager.merge(tipoUsu);
	}
	
	@Test
	@DisplayName("Eliminar Tipo Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	void cTest() {
		
		assertNotNull(entityManager);
		TipoUsuario tipoUsu = entityManager.find(TipoUsuario.class, tiusId);
		assertNotNull(tipoUsu, "El usuario no existe");
		
		entityManager.remove(tipoUsu);
	}
	
	private final static Logger log = LoggerFactory.getLogger(TestClienteSpring.class);
	
	@Test
	@DisplayName("Consultar tipos de Usuario")
	void dTest() {
		
		assertNotNull(entityManager);
		String JPQL = "SELECT tipoUsu FROM TipoUsuario tipoUsu";
		List<TipoUsuario> losTipUsuario = entityManager.createQuery(JPQL).getResultList();
		
		losTipUsuario.forEach(tipoUsu->{
			log.info("Tipo Usuario id : "+tipoUsu.getTiusId());
			log.info("TipoUsuario nombre"+ tipoUsu.getNombre());			
		});
		
		/*
		 * for (Cliente cliente : losCliente){
		   log.info("Clietnte Id: "+cliente.getClieId());
		   log.info("Cliente nombre: "+cliente.getNombre());
		  }
		 */
	}

}
