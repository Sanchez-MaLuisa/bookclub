package com.example.demo.service.owner.security;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Owner;

public interface IOwnerSecurityService {
    Boolean checkIfValidToken(Owner owner, String token);
}
