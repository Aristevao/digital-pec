package com.fho.digitalpec.api.unit.repository;

import java.util.List;

import com.fho.digitalpec.api.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findAllByOrderByNameAsc();
}
