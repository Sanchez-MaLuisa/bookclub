package com.example.demo.service.owner;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.persistence.repository.IOwnerRepository;
import com.example.demo.service.owner.security.IOwnerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService implements IOwnerService {
    private IOwnerRepository ownerRepository;

    @Autowired
    public OwnerService(IOwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner getOwnerById(Long ownerId) throws ResourceNotFoundException {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found by Id: " + ownerId));
        return owner;
    }

    @Override
    public Owner modifyOwner(Long ownerId, String username, String password, String email)
            throws ResourceNotFoundException {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found by Id: " + ownerId));
        owner.setUsername(username);
        owner.setPassword(password);
        owner.setEmail(email);
        return owner;
    }

    @Override
    public Owner saveOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(Owner owner) {
        ownerRepository.delete(owner);
    }
}
