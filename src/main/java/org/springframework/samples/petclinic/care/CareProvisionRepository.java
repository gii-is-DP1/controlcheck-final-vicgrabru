package org.springframework.samples.petclinic.care;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CareProvisionRepository extends CrudRepository<CareProvision, Integer>{
    List<CareProvision> findAll();        
    Optional<CareProvision> findById(int id);
    CareProvision save(CareProvision p);
    
    @Query("SELECT care FROM Care care")
	List<Care> findAllCares();
    
    @Query("SELECT care FROM Care care WHERE :petTypeName IN (SELECT name FROM care.compatiblePetTypes)")
    List<Care> findCompatibleCares(String petTypeName);
    
    @Query("SELECT care FROM Care care WHERE care.name LIKE :name")
    Care findCareByName(String name);
    
    @Query("SELECT provision FROM CareProvision provision WHERE provision.visit.id LIKE :visitId")
    List<CareProvision> findCaresProvidedByVisitId(Integer visitId);
}
