package org.springframework.samples.petclinic.care;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.Visit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CareController {
	
	private final String VIEWS_CREATE_OR_UPDATE_PROVIDED_CARE_FORM = "care/createOrUpdateProvidedCareForm";
	
	
	@Autowired
	private CareService careService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	public CareController(CareService careService, PetService petService) {
		 this.careService = careService;
		 this.petService = petService;
	}
	 
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	 
	@GetMapping("/visit/{visitId}/care/create")
	public String initCreateOrUpdateCareForm(ModelMap model, @PathVariable("visitId") Integer visitId) {
		Visit v = petService.findVisitById(visitId);
		CareProvision careProvision = new CareProvision();
		
		
		Pet p = v.getPet();
		
		List<Care> compC =  careService.getAllCompatibleCares(p.getType().getName());
		
		careProvision.setVisit(v);
		
		
		model.put("cares", compC);
		model.put("providedCare", careProvision);
		return VIEWS_CREATE_OR_UPDATE_PROVIDED_CARE_FORM;
	}
}
