package ru.sber.mobile_phone_shop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sber.mobile_phone_shop.exception.PhoneNotFoundException;
import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.repository.PhoneRepository;
import ru.sber.mobile_phone_shop.service.PhoneService;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneRequest;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneResponse;
import ru.sber.mobile_phone_shop.servlet.mapper.PhoneMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PhoneServiceTest {
    PhoneRepository repository = Mockito.mock(PhoneRepository.class);
    PhoneMapper mapper = new PhoneMapper();
    PhoneService phoneService = new PhoneServiceImpl(repository, mapper);
    Phone phone;
    PhoneRequest phoneRequest;
    PhoneResponse phoneResponse;

    @BeforeEach
    void setUp() {
        phone = new Phone(
                1L,
                "apple",
                "15 pro max",
                UUID.randomUUID(),
                "white",
                LocalDate.of(2024, 8, 16)
        );
        phoneResponse = new PhoneResponse(
                1L,
                "apple",
                "15 pro max",
                phone.getSerialNumber(),
                "white",
                LocalDate.of(2024, 8, 16)
        );
        phoneRequest = new PhoneRequest(
                "apple",
                "15 pro max",
                phone.getSerialNumber(),
                "white",
                LocalDate.of(2024, 8, 16)
        );
    }

    @Test
    void getByIdPositiveTest() {
        when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));

        phoneResponse = phoneService.getPhoneById(phone.getId());
        assertEquals(phone.getId(), phoneResponse.getId());
        assertEquals(phone.getColor(), phoneResponse.getColor());
        assertEquals(phone.getSerialNumber(), phoneResponse.getSerialNumber());
        verify(repository, times(1)).findById(phone.getId());
    }

    @Test
    void getByIdNegativeTest() {
        when(repository.findById(phone.getId())).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(PhoneNotFoundException.class, () -> phoneService.getPhoneById(phone.getId()));
        assertNotNull(ex);
        verify(repository, times(1)).findById(phone.getId());
    }

    @Test
    void getAllPhonesPositiveTest() {
        when(repository.findAll()).thenReturn(List.of(phone));

        List<PhoneResponse> responses = phoneService.getAllPhones();
        assertEquals(1, responses.size());
        assertEquals(phone.getSerialNumber(), responses.get(0).getSerialNumber());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAllPhonesNegativeTest() {
        when(repository.findAll()).thenReturn(List.of());
        List<PhoneResponse> responses = phoneService.getAllPhones();
        assertEquals(0, responses.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createPositiveTest() {
        when(repository.save(any(Phone.class))).thenReturn(phone);
        phoneResponse = phoneService.create(phoneRequest);
        assertEquals(phoneResponse.getSerialNumber(), phone.getSerialNumber());
        verify(repository, times(1)).save(any(Phone.class));
    }

    @Test
    void updatePositiveTest() {
        when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        when(repository.update(phone.getId(), phone)).thenReturn(phone);
        phoneResponse = phoneService.update(phone.getId(), phoneRequest);
        assertEquals(phone.getSerialNumber(), phoneResponse.getSerialNumber());
        verify(repository, times(1)).update(phone.getId(), phone);
        verify(repository, times(1)).findById(phone.getId());
    }

    @Test
    void updateNegativeTest() {
        when(repository.findById(phone.getId())).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(PhoneNotFoundException.class, () -> phoneService.update(phone.getId(), phoneRequest));
        assertNotNull(ex);
        verify(repository, times(1)).findById(phone.getId());
    }

    @Test
    void deletePositiveTest() {
        when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        when(repository.delete(phone)).thenReturn(phone);
        phoneResponse = phoneService.delete(phone.getId());
        assertEquals(phone.getSerialNumber(), phoneResponse.getSerialNumber());
        verify(repository, times(1)).findById(phone.getId());
        verify(repository, times(1)).delete(phone);
    }

}
