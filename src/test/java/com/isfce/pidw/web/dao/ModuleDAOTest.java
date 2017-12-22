package com.isfce.pidw.web.dao;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.isfce.pidw.config.DataConfig;
import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.data.IModuleJpaDAO;
import com.isfce.pidw.model.Cours;
import com.isfce.pidw.model.Module;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "testU")
@ContextConfiguration(classes = DataConfig.class)
public class ModuleDAOTest {
	@Autowired
	ICoursJpaDAO coursDAO;

	@Autowired
	IModuleJpaDAO moduleDAO;

	@Test
	@Transactional
	public void testSaveGet() throws ParseException {
		// Ajout d'un cours IPID
		Cours pid = new Cours("IPID", "Test JPA", (short) 60);
		pid=coursDAO.save(pid);
		pid.addSection("Informatique");
		
		// Ajout d'un module associé
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long cpt = moduleDAO.count();
		Module module = new Module("4IPID-1-A", sdf.parse("4/9/2017"), sdf.parse("22/1/2018"), Module.MAS.SOIR, pid);
		Module saved = moduleDAO.save(module);
		assertEquals(module, saved);
		//Recharge le module
		Module moduleGet = moduleDAO.getOne(saved.getCode());
		assertEquals(saved, moduleGet);
		assertEquals(cpt + 1, moduleDAO.count());
		
		List<Module> liste= moduleDAO.getModulesAPMFromSection("Informatique",Module.MAS.SOIR);
		assertEquals(liste.get(0),moduleGet);
		assertEquals(pid,liste.get(0).getCours());
		moduleDAO.delete(saved.getCode());
		assertEquals(cpt, moduleDAO.count());
	}
}
