package com.ossia.test.repository.impl;

import org.springframework.stereotype.Repository;

import com.ossia.test.domain.ProfilHisto;
import com.ossia.test.repository.ProfilHistoRepository;

@Repository
public class ProfilHistoRepositoryImpl extends AbstractRepositoryImpl<ProfilHisto, Integer> implements ProfilHistoRepository {
}