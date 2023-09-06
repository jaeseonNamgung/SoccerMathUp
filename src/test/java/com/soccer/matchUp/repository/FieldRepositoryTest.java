package com.soccer.matchUp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
class FieldRepositoryTest {

    @Autowired
    private EntityManager entityManager;




}