package com.example.demo.mapper;

import com.example.demo.controller.model.CreatedOwnerDto;
import com.example.demo.controller.model.OwnerDto;
import com.example.demo.persistence.entity.Owner;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerMapper implements IOwnerMapper{
    @Override
    public Owner ownerDtoToOwnerModel(@NotNull OwnerDto dto) {
        Owner owner = new Owner();
        owner.setUsername(dto.getUsername());
        owner.setEmail(dto.getEmail());
        owner.setPassword(dto.getPassword());
        owner.setReviews(new ArrayList<>());
        owner.setComments(new ArrayList<>());
        return owner;
    }

    @Override
    public CreatedOwnerDto ownerModelToCreatedOwnerDto(@NotNull Owner owner) {
        CreatedOwnerDto dto = new CreatedOwnerDto();
        dto.setId(owner.getId());
        dto.setUsername(owner.getUsername());
        dto.setEmail(owner.getEmail());
        dto.setPassword(owner.getPassword());
        List<Long> reviewsIds = new ArrayList<>();
        for(int i = 0; i < owner.getReviews().size(); i++) {
            reviewsIds.add(owner.getReviews().get(i).getId());
        }
        dto.setReviewsId(reviewsIds);
        List<Long> commentsIds = new ArrayList<>();
        for(int i = 0; i < owner.getComments().size(); i++) {
            commentsIds.add(owner.getComments().get(i).getId());
        }
        dto.setCommentsId(commentsIds);

        return dto;
    }

    @Override
    public List<CreatedOwnerDto> ownerModelListToCreatedOwnerDtoList(@NotNull List<Owner> owners) {
        List<CreatedOwnerDto> ownerDtos = new ArrayList<>();
        for(int i = 0; i < owners.size(); i++) {
            ownerDtos.add(ownerModelToCreatedOwnerDto(owners.get(i)));
        }
        return ownerDtos;
    }

    @Override
    public void modifyOwnerFromOwnerDto(Owner owner, OwnerDto ownerInput) {
        owner.setUsername(ownerInput.getUsername());
        owner.setPassword(ownerInput.getPassword());
        owner.setEmail(ownerInput.getEmail());
    }
}
