package org.Camp.Repository;

import org.Camp.Model.Entities.Camp;

import java.util.List;
import java.util.Optional;

public interface CampRepository {

    List<Camp> findAll();
    Optional<Camp> findById(Long id);
    int save(Camp camp);
    int update(Camp camp);

    int delete(Long id);
}
