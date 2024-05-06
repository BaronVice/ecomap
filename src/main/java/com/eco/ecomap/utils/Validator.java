package com.eco.ecomap.utils;

import com.eco.ecomap.exceptions.ApplicationException;
import com.eco.ecomap.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class Validator {
    private final CityRepository cityRepository;

    public void ifCityExists(String name){
//        if (! cityRepository.existsByName(name)){
//            throw new ApplicationException(
//                    "City is not found or not implemented",
//                    HttpStatus.NOT_FOUND
//            );
//        }
    }
}
