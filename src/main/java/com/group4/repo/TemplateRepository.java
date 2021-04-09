package com.group4.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.TemplateDTO;
import com.group4.entity.Template;


public interface TemplateRepository extends CrudRepository<Template, Integer>{

	List<Template> findAll();
	
	TemplateDTO findByTemplateName(String templateName);
	
	TemplateDTO findByTemplateId(Integer templateId);
}
