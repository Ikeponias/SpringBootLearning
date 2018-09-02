package com.ikeda.mydata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikeda.mydata.models.MyData;


@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {

}
