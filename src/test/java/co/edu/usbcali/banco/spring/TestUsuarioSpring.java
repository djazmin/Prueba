package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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
import co.edu.usbcali.banco.domain.Usuario;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")

class TestUsuarioSpring {

	@PersistenceContext
	EntityManager entityManager;
	String usuUsuario = "yazminag92";
	
	@DisplayName("Crear Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	@Test
	void aTest() {
		assertNotNull(entityManager);
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNull(usuario, "El usuario ya existe");
		
		usuario = new Usuario();
		usuario.setActivo("S");
		usuario.setClave("yazminAG92");
		usuario.setIdentificacion(new BigDecimal(12312));
		usuario.setNombre("Yazmin Anacona");
		usuario.setUsuUsuario(usuUsuario);
		TipoUsuario tipoUsu = entityManager.find(TipoUsuario.class, 2L);
		assertNotNull(tipoUsu);
		usuario.setTipoUsuario(tipoUsu);
		
		entityManager.persist(usuario);
	}
	
	@Test
	@DisplayName("Modificar Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	void bTest() {
		
		assertNotNull(entityManager);	
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El usuario no existe");
		usuario.setActivo("N");
		usuario.setClave("yazminAG92");
		usuario.setIdentificacion(new BigDecimal(1234124));
		usuario.setNombre("Maria Anacona");
		entityManager.merge(usuario);
	}
	
	@Test
	@DisplayName("Eliminar Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	void cTest() {
		
		assertNotNull(entityManager);
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El usuario no existe");
		
		entityManager.remove(usuario);
	}
	
	private final static Logger log = LoggerFactory.getLogger(TestClienteSpring.class);
	
	@Test
	@DisplayName("Consultar Usuario")
	void dTest() {
		
		assertNotNull(entityManager);
		String JPQL = "SELECT usu FROM Usuario usu";
		List<Usuario> losUsuario = entityManager.createQuery(JPQL).getResultList();
		
		losUsuario.forEach(usuario->{
			log.info("Usuario login : "+usuario.getNombre());
			log.info("Usuario nombre: "+ usuario.getNombre());			
		});
		
		/*
		 * for (Cliente cliente : losCliente){
		   log.info("Clietnte Id: "+cliente.getClieId());
		   log.info("Cliente nombre: "+cliente.getNombre());
		  }
		 */
	}


}
