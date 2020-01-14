package com.example.demo.controller;

import com.example.demo.controller.model.CreatedOwnerDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.controller.model.OwnerDto;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.service.owner.IOwnerService;
import com.example.demo.mapper.CreateOwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class OwnerController {
    private IOwnerService ownerService;

    @Autowired
    public OwnerController(IOwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    public List<CreatedOwnerDto> listAllOwners() {
        List<Owner> owners = ownerService.getAllOwners();
        List<CreatedOwnerDto> ownerDtos = new ArrayList<>();
        for(int i = 0; i < owners.size(); i++) {
            ownerDtos.add(CreateOwnerMapper.ownerModelToCreatedOwnerDto(owners.get(i)));
        }
        return ownerDtos;
    }

    @GetMapping("/owners/{id}")
    public CreatedOwnerDto getOwnerById(@PathVariable(value="id") Long ownerId)
            throws ResourceNotFoundException {
        Owner owner = ownerService.getOwnerById(ownerId);
        return CreateOwnerMapper.ownerModelToCreatedOwnerDto(owner);
    }

    @PostMapping("/owners")
    public CreatedOwnerDto createOwner(@Valid @RequestBody OwnerDto ownerInput) {
        Owner owner = CreateOwnerMapper.ownerDtoToOwnerModel(ownerInput);
        Owner savedOwner = ownerService.saveOwner(owner);
        return CreateOwnerMapper.ownerModelToCreatedOwnerDto(savedOwner);
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<CreatedOwnerDto> updateOwner(@RequestParam String token,
                                             @PathVariable(value="id") Long ownerId,
                                             @Valid @RequestBody OwnerDto ownerInput)
            throws ResourceNotFoundException, Exception {
        ownerService.getOwnerIfValid(ownerId, token);
        Owner owner = ownerService.modifyOwner( ownerId, ownerInput.getUsername(),
                                                ownerInput.getPassword(), ownerInput.getEmail());
        Owner savedOwner = ownerService.saveOwner(owner);
        return ResponseEntity.ok(CreateOwnerMapper.ownerModelToCreatedOwnerDto(savedOwner));
    }

    @DeleteMapping("/owners/{id}")
    public Map<String, Boolean> deleteOwner(@RequestParam String token,
                                            @PathVariable(value="id") Long ownerId,
                                            @Valid @RequestBody OwnerDto ownerInput)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerIfValid(ownerId, token);
        ownerService.deleteOwner(owner);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
