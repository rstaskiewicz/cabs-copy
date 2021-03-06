package io.legacyfighter.cabs.service;

import io.legacyfighter.cabs.dto.DriverDTO;
import io.legacyfighter.cabs.entity.Driver;
import io.legacyfighter.cabs.entity.DriverAttribute;
import io.legacyfighter.cabs.entity.DriverLicense;
import io.legacyfighter.cabs.entity.Transit;
import io.legacyfighter.cabs.money.Money;
import io.legacyfighter.cabs.repository.DriverAttributeRepository;
import io.legacyfighter.cabs.repository.DriverRepository;
import io.legacyfighter.cabs.repository.TransitRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.legacyfighter.cabs.entity.DriverLicense.withLicense;

@Service
public class DriverService {

    public static final String DRIVER_LICENSE_REGEX = "^[A-Z9]{5}\\d{6}[A-Z9]{2}\\d[A-Z]{2}$";

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverAttributeRepository driverAttributeRepository;

    @Autowired
    private TransitRepository transitRepository;

    @Autowired
    private DriverFeeService driverFeeService;

    public Driver createDriver(String license, String lastName, String firstName, Driver.Type type, Driver.Status status, String photo) {
        Driver driver = new Driver();
        if (status.equals(Driver.Status.ACTIVE)) {
            driver.setDriverLicense(withLicense(license));
        } else {
            driver.setDriverLicense(DriverLicense.withoutValidation(license));
        }
        driver.setLastName(lastName);
        driver.setFirstName(firstName);
        driver.setStatus(status);
        driver.setType(type);
        if (photo != null && !photo.isEmpty()) {
            if (Base64.isBase64(photo)) {
                driver.setPhoto(photo);
            } else {
                throw new IllegalArgumentException("Illegal photo in base64");
            }
        }
        return driverRepository.save(driver);
    }

    @Transactional
    public void changeLicenseNumber(String newLicense, Long driverId) {
        Driver driver = driverRepository.getOne(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Driver does not exists, id = " + driverId);
        }
        driver.setDriverLicense(withLicense(newLicense));

        if (!driver.getStatus().equals(Driver.Status.ACTIVE)) {
            throw new IllegalStateException("Driver is not active, cannot change license");
        }


    }


    @Transactional
    public void changeDriverStatus(Long driverId, Driver.Status status) {
        Driver driver = driverRepository.getOne(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Driver does not exists, id = " + driverId);
        }
        if (status.equals(Driver.Status.ACTIVE)) {
            try {
                driver.setDriverLicense(withLicense(driver.getDriverLicense().asString()));
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException(e);
            }
        }
        driver.setStatus(status);
    }

    public void changePhoto(Long driverId, String photo) {
        Driver driver = driverRepository.getOne(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Driver does not exists, id = " + driverId);
        }
        if (photo != null && !photo.isEmpty()) {
            if (Base64.isBase64(photo)) {
                driver.setPhoto(photo);
            } else {
                throw new IllegalArgumentException("Illegal photo in base64");
            }
        }
        driver.setPhoto(photo);
        driverRepository.save(driver);
    }

    public Money calculateDriverMonthlyPayment(Long driverId, int year, int month) {
        Driver driver = driverRepository.getOne(driverId);
        if (driver == null)
            throw new IllegalArgumentException("Driver does not exists, id = " + driverId);

        YearMonth yearMonth = YearMonth.of(year, month);
        Instant from = yearMonth
                .atDay(1).atStartOfDay(ZoneId.systemDefault())

                .toInstant();
        Instant to = yearMonth

                .atEndOfMonth().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<Transit> transitsList = transitRepository.findAllByDriverAndDateTimeBetween(driver, from, to);

        Money sum = transitsList.stream()
                .map(t -> driverFeeService.calculateDriverFee(t.getId())).reduce(Money.ZERO, Money::add);

        return sum;
    }

    public Map<Month, Money> calculateDriverYearlyPayment(Long driverId, int year) {
        Map<Month, Money> payments = new HashMap<>();
        for (Month m : Month.values()) {
            payments.put(m, calculateDriverMonthlyPayment(driverId, year, m.getValue()));
        }
        return payments;
    }

    @Transactional
    public DriverDTO loadDriver(Long driverId) {
        Driver driver = driverRepository.getOne(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Driver does not exists, id = " + driverId);
        }
        return new DriverDTO(driver);
    }

    public void addAttribute(Long driverId, DriverAttribute.DriverAttributeName attr, String value) {
        Driver driver = driverRepository.getOne(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Driver does not exists, id = " + driverId);
        }
        driverAttributeRepository.save(new DriverAttribute(driver, attr, value));

    }


}
