package com.example.demo.mapper;

import com.example.demo.controller.model.CreatedOwnerDto;
import com.example.demo.controller.model.OwnerDto;
import com.example.demo.persistence.entity.Owner;

import java.util.ArrayList;
import java.util.List;

public class CreateOwnerMapper {
    public static Owner ownerDtoToOwnerModel(OwnerDto dto) {
        Owner owner = new Owner();
        owner.setUsername(dto.getUsername());
        owner.setEmail(dto.getEmail());
        owner.setPassword(dto.getPassword());
        owner.setReviews(new ArrayList<>());
        owner.setComments(new ArrayList<>());
        return owner;
    }

    public static CreatedOwnerDto ownerModelToCreatedOwnerDto(Owner owner) {
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
}
