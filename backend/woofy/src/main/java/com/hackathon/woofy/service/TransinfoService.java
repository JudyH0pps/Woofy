package com.hackathon.woofy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Transinfo;
import com.hackathon.woofy.repo.TransinfoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransinfoService {

	private final TransinfoRepo transinfoRepo;
	
	@Transactional
	public void saveTransinfo(Transinfo transinfo) {
		transinfoRepo.save(transinfo);
	}
	
	@Transactional
	public List<Transinfo> findByParent(Parent parent){
		return transinfoRepo.findByParent(parent);
	}
	
	@Transactional
	public List<Transinfo> findByChild(Long child_id){
		return transinfoRepo.findByChild(child_id);
	}
}
