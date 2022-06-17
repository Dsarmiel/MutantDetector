package com.example.mutantdetector.dao;

import com.example.mutantdetector.models.AdnVerifications;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MutantDetectorDao {
    @PersistenceContext
    EntityManager entityManager;

    public void saveIsMutant(AdnVerifications adnVerificados) {
        entityManager.merge(adnVerificados);
    }
    public List getMutantByDna(String dna){
        String query = "FROM AdnVerifications WHERE dna = :dna";
        return entityManager.createQuery(query)
                .setParameter("dna", dna)
                .getResultList();
    }
    public List getAdnVerifications(boolean is_mutant) {
        return entityManager.createQuery("FROM AdnVerifications WHERE is_mutant IS :is_mutant")
                .setParameter("is_mutant", is_mutant)
                .getResultList();
    }
}
