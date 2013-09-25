package com.ossia.test.repository;

import java.util.List;

import com.ossia.test.domain.PropositionReponse;

public interface PropositionReponseRepository extends AbstractRepository<PropositionReponse, Integer>{

	List<PropositionReponse> getAllPropositionReponseByQuestionId(Integer id);
}
