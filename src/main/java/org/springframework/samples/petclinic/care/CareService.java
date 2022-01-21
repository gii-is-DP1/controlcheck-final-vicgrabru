package org.springframework.samples.petclinic.care;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.stereotype.Service;


@Service
public class CareService {
	@Autowired
	private CareProvisionRepository careProvisionRepository;
	
	@Autowired
	public CareService(CareProvisionRepository careProvisionRepository) {
		this.careProvisionRepository = careProvisionRepository;
		
	}
	
    public List<Care> getAllCares(){
        return null;
    }

    public List<Care> getAllCompatibleCares(String petTypeName){
        return careProvisionRepository.findCompatibleCares(petTypeName);
    }

    public Care getCare(String careName) {
        return careProvisionRepository.findCareByName(careName);
    }

    
    @Transactional(readOnly = false, rollbackFor = {NonCompatibleCaresException.class, UnfeasibleCareException.class})
    public CareProvision save(CareProvision p) throws NonCompatibleCaresException, UnfeasibleCareException {
    	List<CareProvision> cuidadosPrevios = getCaresProvided(p.getVisit().getId());
    	Set<PetType> mascotasCompatibles = p.getCare().getCompatiblePetTypes();
    	PetType tipoMascota = p.getVisit().getPet().getType();
    	if(mascotasCompatibles.contains(tipoMascota)) {
    		if(cuidadosPrevios.stream().allMatch(x -> !x.getCare().getIncompatibleCares().contains(p.getCare()))) {
    			return careProvisionRepository.save(p);
    		}
    		throw new NonCompatibleCaresException();
    	}
    	throw new UnfeasibleCareException();
    }

    public List<CareProvision> getAllCaresProvided(){
        return careProvisionRepository.findAll();
    }

    public List<CareProvision> getCaresProvided(Integer visitId){
        return careProvisionRepository.findCaresProvidedByVisitId(visitId);

    }
    
}
