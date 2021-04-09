package com.group4.serviceImplement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.group4.DTO.PostDTO;
import com.group4.entity.Post;
import com.group4.repo.PostRepository;
import com.group4.service.PostService;

@Service
public class PostServiceImplement implements PostService{

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<PostDTO> getTop5PostByCategoryIdAndStatus(List<Integer> categoryID, boolean status) {
		return postRepo.findTop5ByCategoryIdInAndStatusOrderByPostIdDesc(categoryID, status);
	}

	@Override
	public List<PostDTO> getTop20PostByCategoryIdAndStatus(List<Integer> categoryID, boolean status) {
		return postRepo.findTop20ByCategoryIdInAndStatusOrderByPostIdDesc(categoryID, status);
	}

	@Override
	public List<PostDTO> getTop12PostByCategoryIdAndStatus(List<Integer> categoryID, boolean status) {
		return postRepo.findTop12ByCategoryIdInAndStatusOrderByPostIdDesc(categoryID, status);
	}

	@Override
	public int getMaxPostID() {
		return postRepo.getMaxPostID();
	}

	@Override
	public boolean createUpdatePost(PostDTO postDTO) {
		// TODO Auto-generated method stub
		try {
			Post post = mapper.map(postDTO, Post.class);
			postRepo.save(post);
			return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public PostDTO findByPostIDAndStatus(int postID, boolean status) {
		// TODO Auto-generated method stub
		return postRepo.findByPostIdAndStatus(postID, status);
	}

	@Override
	public List<PostDTO> findByTitleContainingAndCategoryId(String title, int categoryID) {
		return postRepo.findByTitleContainingAndCategoryId(title, categoryID);
	}

	@Override
	public PostDTO findByPostId(int postID) {
		return postRepo.findByPostId(postID);
	}

	@Override
	public List<PostDTO> getAllPosts() {
		List<Post> postList = postRepo.findAll();
		List<PostDTO> postDTOList = new ArrayList<PostDTO>();
		for (Iterator<Post> iterator = postList.iterator(); iterator.hasNext();) {
			Post post = (Post) iterator.next();
			PostDTO postDTO = new PostDTO(post.getPostId(), post.getTitle(), post.getDate(), post.getDescription(),
					post.getImage(), post.getPostContent(), post.getAuthor(), post.getCategoryId(), post.getTemplateId(),
					post.isStatus());
			postDTOList.add(postDTO);
		}
		return postDTOList;
	}

	@Override
	public Integer getTotalPosts() {
		return postRepo.findAll().size();
	}

	@Override
	public List<PostDTO> findByCategoryId(int categoryID) {
		// TODO Auto-generated method stub
		return postRepo.findByCategoryId(categoryID);
	}

	@Override
	public Page<PostDTO> findByCategoryIdInOrderByPostIdDesc(List<Integer> listCategoryId, Pageable pageable) {
		// TODO Auto-generated method stub
		return postRepo.findByCategoryIdInAndStatusTrueOrderByPostIdDesc(listCategoryId, pageable);
	}

	
}
