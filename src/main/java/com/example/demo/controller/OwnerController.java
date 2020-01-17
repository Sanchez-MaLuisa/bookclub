package com.example.demo.controller;

import com.example.demo.controller.model.CreatedOwnerDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.controller.model.OwnerDto;
import com.example.demo.mapper.IOwnerMapper;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.service.owner.IOwnerService;
import com.example.demo.mapper.OwnerMapper;
import com.example.demo.service.owner.security.IOwnerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OwnerController {
    private IOwnerService ownerService;
    private IOwnerSecurityService ownerSecurityService;
    private IOwnerMapper ownerMapper;
    @Autowired
    public OwnerController(IOwnerService ownerService, IOwnerSecurityService ownerSecurityService, IOwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerSecurityService = ownerSecurityService;
        this.ownerMapper = ownerMapper;
    }

    @GetMapping("/owners")
    public List<CreatedOwnerDto> listAllOwners() {
        List<Owner> owners = ownerService.getAllOwners();
        List<CreatedOwnerDto> ownerDtos = ownerMapper.ownerModelListToCreatedOwnerDtoList(owners);
        return ownerDtos;
    }

    @GetMapping("/owners/{id}")
    public CreatedOwnerDto getOwnerById(@PathVariable(value="id") Long ownerId)
            throws ResourceNotFoundException {
        Owner owner = ownerService.getOwnerById(ownerId);
        return ownerMapper.ownerModelToCreatedOwnerDto(owner);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/owners")
    public CreatedOwnerDto createOwner(@Valid @RequestBody OwnerDto ownerInput) {
        Owner owner = ownerMapper.ownerDtoToOwnerModel(ownerInput);
        Owner savedOwner = ownerService.saveOwner(owner);
        return ownerMapper.ownerModelToCreatedOwnerDto(savedOwner);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/owners/{id}")
    public CreatedOwnerDto updateOwner(@RequestHeader(value = "Authorization") String token,
                                       @PathVariable(value="id") Long ownerId,
                                       @Valid @RequestBody OwnerDto ownerInput)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);

        ownerMapper.modifyOwnerFromOwnerDto(owner, ownerInput);

        Owner savedOwner = ownerService.saveOwner(owner);
        return ownerMapper.ownerModelToCreatedOwnerDto(savedOwner);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/owners/{id}")
    public CreatedOwnerDto deleteOwner(@RequestHeader(value = "Authorization") String token,
                                       @PathVariable(value="id") Long ownerId,
                                       @Valid @RequestBody OwnerDto ownerInput)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);
        ownerService.deleteOwner(owner);
        return ownerMapper.ownerModelToCreatedOwnerDto(owner);
    }
}
