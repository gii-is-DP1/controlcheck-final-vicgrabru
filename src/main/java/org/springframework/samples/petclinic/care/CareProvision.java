package org.springframework.samples.petclinic.care;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.pet.Visit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="provisions")
public class CareProvision extends BaseEntity{
	
	@NotNull
	@PositiveOrZero
    double duration;
	
	@ManyToOne
	@JoinColumn(name="visit_id")
    Visit visit;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="care_id")
    Care care;   
}
