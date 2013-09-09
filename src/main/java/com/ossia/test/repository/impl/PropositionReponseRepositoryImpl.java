package com.ossia.test.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.repository.PropositionReponseRepository;

@Repository
public class PropositionReponseRepositoryImpl extends AbstractRepositoryImpl<PropositionReponse, Integer> implements PropositionReponseRepository {

}
