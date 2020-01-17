package com.example.demo.mapper;

import com.example.demo.controller.model.CreatedOwnerDto;
import com.example.demo.controller.model.OwnerDto;
import com.example.demo.persistence.entity.Owner;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface IOwnerMapper {
    Owner ownerDtoToOwnerModel(@NotNull OwnerDto dto);
    CreatedOwnerDto ownerModelToCreatedOwnerDto(@NotNull Owner owner);
    List<CreatedOwnerDto> ownerModelListToCreatedOwnerDtoList(@NotNull List<Owner> owners);
    void modifyOwnerFromOwnerDto(Owner owner, OwnerDto ownerInput);
}
