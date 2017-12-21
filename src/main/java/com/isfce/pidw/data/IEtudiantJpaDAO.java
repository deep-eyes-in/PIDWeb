package com.isfce.pidw.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isfce.pidw.model.Etudiant;

@Repository
public interface IEtudiantJpaDAO extends JpaRepository<Etudiant, Long> {

}
