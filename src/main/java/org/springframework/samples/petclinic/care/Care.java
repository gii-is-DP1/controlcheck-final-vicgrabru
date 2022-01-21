package org.springframework.samples.petclinic.care;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.pet.PetType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cares")
public class Care extends BaseEntity{
	@NotEmpty
	@Size(min=5, max=30)
	@Column(unique=true)
    String name;
	
	@NotEmpty
    String description;
	
	@NotEmpty
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="pet_type_id")
    Set<PetType> compatiblePetTypes;
	
	@ManyToMany(cascade=CascadeType.ALL, targetEntity=Care.class)
	@JoinColumn(name="care_id")
	Set<Care> incompatibleCares;
}
