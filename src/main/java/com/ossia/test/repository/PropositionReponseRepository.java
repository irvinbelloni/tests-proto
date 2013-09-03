package com.ossia.test.repository;

import com.ossia.test.domain.PropositionReponse;

public interface PropositionReponseRepository {

	Integer createPropositionReponse (PropositionReponse pr) ; 
	
	PropositionReponse getPropositionReponsebyId (Integer id) ; 
	
	void deletePropositionReponse (PropositionReponse pr) ; 
}
