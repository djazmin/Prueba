package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.domain.TipoUsuario;
import co.edu.usbcali.banco.domain.Usuario;

class TestUsuarioJPA {

	String usuUsuario = "yazminag92";
	@DisplayName("Crear Usuario")
	@Test
	void aTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		
		assertNull(usuario, "El usuario ya existe");
		
		usuario = new Usuario();
		usuario.setActivo("S");
		usuario.setClave("123ABC");
		usuario.setIdentificacion(new BigDecimal(12312));
		usuario.setNombre("Maria Perez");
		usuario.setUsuUsuario(usuUsuario);
		
		TipoUsuario tipoUsu = entityManager.find(TipoUsuario.class, 2L);
		assertNotNull(tipoUsu);
		usuario.setTipoUsuario(tipoUsu);
		
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Modificar Usuario")
	void bTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El cliente no existe");
		
		usuario.setActivo("N");
		usuario.setClave("123456789ABC");
		usuario.setIdentificacion(new BigDecimal(12312));
		usuario.setNombre("Maria Perez de Benitez");		
		
		TipoUsuario tipoUsu = entityManager.find(TipoUsuario.class, 1L);
		assertNotNull(tipoUsu);
		usuario.setTipoUsuario(tipoUsu);
		
		entityManager.getTransaction().begin();
		entityManager.merge(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Eliminar Usuario")
	void cTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El usuario no existe");
		
		entityManager.getTransaction().begin();
		entityManager.remove(usuario);
		entityManager.getTransaction().commit();
	}
	
	private final static Logger log = LoggerFactory.getLogger(TestClienteJPA.class);
	
	@Test
	@DisplayName("Consultar Usuario")
	void dTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "Entity Manager Factory es nulo");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "no nulo");
		
		String JPQL = "SELECT usu FROM Usuario usu";
		List<Usuario> losUsuarios = entityManager.createQuery(JPQL).getResultList();
		
		losUsuarios.forEach(usuario->{
			log.info("usuario: "+usuario.getUsuUsuario());
			log.info("nombre: "+usuario.getNombre());			
		});
		
		/*
		 * for (Cliente cliente : losCliente){
		   log.info("Clietnte Id: "+cliente.getClieId());
		   log.info("Cliente nombre: "+cliente.getNombre());
		  }
		 */
	}

}
