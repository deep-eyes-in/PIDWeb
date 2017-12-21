package com.isfce.pidw.web.dao;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.isfce.pidw.config.DataConfig;
import com.isfce.pidw.data.IEtudiantJpaDAO;
import com.isfce.pidw.model.Etudiant;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "testU")
@ContextConfiguration(classes = DataConfig.class)
public class EtudiantDAOTest {

	@Autowired
	IEtudiantJpaDAO etudiantDAO;

	@Test
	@Transactional
	public void testSaveGet() throws ParseException {
		// Ajout d'un cours IPID
		Etudiant test = new Etudiant("Test","test","test@gmail.com","02/647.25.69");
		test=etudiantDAO.save(test);
		assertEquals(new Long(1), test.getId());
		
		test.setTel("02/000.00.00");
		Etudiant saved=etudiantDAO.findOne(test.getId());
		assertEquals(test, saved);
	}

}
