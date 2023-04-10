package org.Camp.Controller;

import org.Camp.Model.Entities.Camp;
import org.Camp.Model.Response.CommonResponse;
import org.Camp.Model.Response.SuccessResponse;
import org.Camp.Service.CampService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/camps")
public class CampApiController {

    private final CampService campService;

    public CampApiController(CampService campService) {
        this.campService = campService;
    }

    @GetMapping
    public ResponseEntity getAllCamps() {
        List<Camp> camps = campService.findAll();
        CommonResponse commonResponse = new SuccessResponse<>("Success Get All Camping Ground", camps);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCampById(@PathVariable("id") Long id) {
        Camp camp = campService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Find Camp With id: "+ id,camp);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity createCamp(@RequestBody Camp camp) {
        Camp createdCamp = campService.save(camp);
        CommonResponse commonResponse = new SuccessResponse<>("Success Creating New Camping Ground", createdCamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCamp(@PathVariable("id") Long id, @RequestBody Camp camp) {
        camp.setId(id);
        Camp updatedCamp = campService.update(camp);
        CommonResponse commonResponse = new SuccessResponse<>("Success Update Camp With id: "+ id,updatedCamp);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCamp(@PathVariable("id") Long id) {
        Camp camp = campService.findById(id);
        campService.delete(id);
        CommonResponse commonResponse = new SuccessResponse<>("Camp With id: "+id+" Has Been deleted",camp);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
