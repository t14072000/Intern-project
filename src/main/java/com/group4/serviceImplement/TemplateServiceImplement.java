package com.group4.serviceImplement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.DTO.PostDTO;
import com.group4.DTO.TemplateDTO;
import com.group4.entity.Template;
import com.group4.repo.PostRepository;
import com.group4.repo.TemplateRepository;
import com.group4.service.TemplateService;

@Service
public class TemplateServiceImplement implements TemplateService{

	@Autowired
	private TemplateRepository templateRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Override
	public List<TemplateDTO> findAll() {
		List <Template> templateList = templateRepo.findAll();
		List<TemplateDTO> templateDTOList = new ArrayList<TemplateDTO>();
		for (Iterator<Template> iterator = templateList.iterator(); iterator.hasNext();) {
			Template template = (Template) iterator.next();
			TemplateDTO templateDTO = new TemplateDTO(template.getTemplateId(), template.getTemplateName(), template.getTemplateLink());
			templateDTOList.add(templateDTO);
		}
		return templateDTOList;
	}

	@Override
	public TemplateDTO findByTemplateName(String templateName) {
		return templateRepo.findByTemplateName(templateName);
	}

	@Override
	public TemplateDTO findByTemplateId(Integer templateId) {
		return templateRepo.findByTemplateId(templateId);
	}

	@Override
	public TemplateDTO findByPostId(Integer postID) {
		PostDTO post = postRepo.findByPostId(postID);
		return templateRepo.findByTemplateId(post.getTemplateId());
	}

}
