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

import co.edu.usbcali.banco.domain.TipoDocumento;
import co.edu.usbcali.banco.domain.TipoUsuario;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
class TestTipoDocumentoSpring {

	@PersistenceContext
	EntityManager entityManager;
	long tdocId=5L;
	
	@DisplayName("Crear Tipo de Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	@Test
	void ATest() {
		assertNotNull(entityManager);
		TipoDocumento tipoDoc = entityManager.find(TipoDocumento.class, tdocId);
		assertNull(tipoDoc, "El tipo de documento ya existe");
		
		tipoDoc = new TipoDocumento();
		tipoDoc.setActivo("S");
		tipoDoc.setNombre("Tipo Prueba");
		tipoDoc.setTdocId(tdocId);
		
		entityManager.persist(tipoDoc);
	}
	
	@Test
	@DisplayName("Modificar Tipo Documento")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	void bTest() {
		
		assertNotNull(entityManager);
		TipoDocumento tipoDoc = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDoc, "El tipo de documento no existe");
		tipoDoc.setActivo("N");
		tipoDoc.setNombre("Tipo prueba modificado");
		
		entityManager.merge(tipoDoc);
	}
	
	@Test
	@DisplayName("Eliminar Tipo Documento")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Rollback(false)
	void cTest() {
		
		assertNotNull(entityManager);
		TipoDocumento tipoDoc = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDoc, "El usuario no existe");
		
		entityManager.remove(tipoDoc);
	}
	
	private final static Logger log = LoggerFactory.getLogger(TestClienteSpring.class);
	
	@Test
	@DisplayName("Consultar tipos de documento")
	void dTest() {
		
		assertNotNull(entityManager);
		String JPQL = "SELECT tipoDoc FROM TipoDocumento tipoDoc";
		List<TipoDocumento> losTipoDoc = entityManager.createQuery(JPQL).getResultList();
		
		losTipoDoc.forEach(tipoDoc->{
			log.info("Tipo Documento id : "+tipoDoc.getTdocId());
			log.info("Tipo Documento nombre"+ tipoDoc.getNombre());			
		});
		
	}

}
