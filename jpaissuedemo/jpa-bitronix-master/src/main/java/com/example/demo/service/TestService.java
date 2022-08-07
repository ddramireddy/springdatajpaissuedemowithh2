package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.SampleEntityRepository;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class TestService {

	@Autowired
	EntityManager em;

	@Transactional(transactionManager = "transactionManager")
	public void doSomething() {
		JpaRepositoryFactory shardFactory = new JpaRepositoryFactory(em);
		shardFactory.getRepository(SampleEntityRepository.class).findAll();
	}
}
