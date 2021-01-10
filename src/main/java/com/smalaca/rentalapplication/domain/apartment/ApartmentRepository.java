package com.smalaca.rentalapplication.domain.apartment;

public interface ApartmentRepository {
    String save(Apartment apartment);

    Apartment findById(String apartmentId);

    boolean existById(String apartmentId);
}
