package com.example.demo.service.owner;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;

import java.util.List;

public interface IOwnerService {
    List<Owner> getAllOwners();
    Owner getOwnerById(Long ownerId) throws ResourceNotFoundException;
    Owner modifyOwner(Long ownerId, String username, String password, String email) throws ResourceNotFoundException;
    Owner saveOwner(Owner owner);
    void deleteOwner(Owner owner);
}