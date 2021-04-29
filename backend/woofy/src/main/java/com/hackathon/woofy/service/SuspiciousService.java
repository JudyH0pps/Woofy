package com.hackathon.woofy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Suspicious;
import com.hackathon.woofy.repo.SuspiciousRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuspiciousService {

	private final SuspiciousRepo suspiciousRepo;
	
	@Transactional
	public Suspicious findById(Long id) {
		return suspiciousRepo.findById(id).get(); // 단건 조회
	}
	
	@Transactional
	public void saveSuspicious(Suspicious suspicious) {
		suspiciousRepo.save(suspicious);
	}
	
	@Transactional
	public void deleteSuspicious(Long id) {
		suspiciousRepo.deleteById(id);
	}
	
	/**
	 * 부모가 설정한 의심 전체조회
	 * @param parent
	 * @return
	 */
	@Transactional
	public List<Suspicious> findByParent(Parent parent) {
		return suspiciousRepo.findByParent(parent);
	}
	
	/**
	 * 자식(1명)의 미션 조회
	 * @param parent
	 * @param child
	 * @return
	 */
	@Transactional
	public List<Suspicious> findByParentAndChild(Parent parent, Child child){
		return suspiciousRepo.findByParentAndChild(parent, child);
	}
}
