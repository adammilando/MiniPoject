package org.Camp.Service;

import org.Camp.Model.Entities.Camp;

import java.util.List;

public interface CampService {
    List<Camp> findAll();
    Camp findById(Long id);
    Camp save(Camp camp);
    Camp update(Camp camp);
    boolean delete(Long id);
}
