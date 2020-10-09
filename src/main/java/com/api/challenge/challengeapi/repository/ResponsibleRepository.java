package com.api.challenge.challengeapi.repository;

import java.util.Optional;

import com.api.challenge.challengeapi.model.Responsible;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsibleRepository extends JpaRepository<Responsible, Long>{
 
    Responsible findByName(String responsibleName);
    Optional<Responsible> findByUsrName(String usrName);
}
