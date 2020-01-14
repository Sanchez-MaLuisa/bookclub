package com.example.demo.service.owner.security;

import com.example.demo.persistence.entity.Owner;
import org.springframework.stereotype.Service;


@Service
public class OwnerSecurityService implements IOwnerSecurityService {
    public OwnerSecurityService() { }

    @Override
    public Boolean checkIfValidToken(Owner owner, String token) {
        String tokenFromOwner = owner.getUsername() + owner.getPassword();
        if(tokenFromOwner.equals(token))
            return true;
        else
            return false;
    }
}
