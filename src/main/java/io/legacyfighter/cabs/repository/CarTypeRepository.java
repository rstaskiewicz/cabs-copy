package io.legacyfighter.cabs.repository;

import io.legacyfighter.cabs.entity.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarTypeRepository extends JpaRepository<CarType, Long> {

    CarType findByCarClass(CarType.CarClass carClass);
    List<CarType> findByStatus(CarType.Status status);
}
