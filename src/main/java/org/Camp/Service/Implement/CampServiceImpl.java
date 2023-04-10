package org.Camp.Service.Implement;

import org.Camp.Exception.CampException;
import org.Camp.Exception.NotFoundException;
import org.Camp.Model.Entities.Camp;
import org.Camp.Repository.CampRepository;
import org.Camp.Service.CampService;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

public class CampServiceImpl implements CampService {

    private final CampRepository campRepository;

    public CampServiceImpl(CampRepository campRepository) {
        this.campRepository = campRepository;
    }

    @Override
    public List<Camp> findAll() {
        try {
            List<Camp> campList = campRepository.findAll();
            if (campList.isEmpty()){
                throw new NotFoundException("Database Empty");
            }
            return campList;
        } catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all camps: " + e.getMessage(), e);
        }
    }

    @Override
    public Camp findById(Long id) {
        try {
            return campRepository.findById(id).orElseThrow(() -> new NotFoundException("id: "+ id +" Does Not Exists"));
        } catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to find camp by id: " + e.getMessage(), e);
        }
    }

    @Override
    public Camp save(Camp camp) {
        try {
            validateCamp(camp);
            validateCampName(camp);
            return campRepository.save(camp) == 1 ? camp : null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save camp: " + e.getMessage(), e);
        }
    }

    @Override
    public Camp update(Camp camp) {
        try {
            findById(camp.getId());
            validateCamp(camp);
            validateCampName(camp);
            return campRepository.update(camp) == 1 ? camp : null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update camp: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            findById(id);
            return campRepository.delete(id) == 1;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete camp: " + e.getMessage(), e);
        }
    }

    private void validateCampName(Camp camp) {
        List<Camp> allCamps = campRepository.findAll();
        boolean isCampNameExists = allCamps.stream().anyMatch(existingCamp -> existingCamp.getName().equalsIgnoreCase(camp.getName()) && !existingCamp.getId().equals(camp.getId()));
        if (isCampNameExists) {
            throw new RuntimeException("Camp name already exists");
        }
    }

    private void validateCamp(Camp camp) {
        try {
            Assert.notNull(camp, "Camp must not be null");
            Assert.hasText(camp.getName(), "Camp name must not be empty");
            Assert.hasText(camp.getLocation(), "Camp location must not be empty");
            Assert.isTrue(camp.getPrice() != null && camp.getPrice().compareTo(BigDecimal.ZERO) >= 0, "Camp price must not be null and must be greater than or equal to 0");
            Assert.isTrue(camp.getStock() >= 0, "Camp stock must be greater than or equal to 0");
        }catch (IllegalArgumentException e){
            throw new CampException(e.getMessage());
        }
    }
}

