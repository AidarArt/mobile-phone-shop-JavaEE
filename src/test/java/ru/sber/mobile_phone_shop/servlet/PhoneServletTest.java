package ru.sber.mobile_phone_shop.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import ru.sber.mobile_phone_shop.model.Phone;
import ru.sber.mobile_phone_shop.service.PhoneService;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneRequest;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneResponse;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PhoneServletTest {
    PhoneServlet servlet = new PhoneServlet();
    PhoneService service = Mockito.mock(PhoneService.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    PrintWriter writer;
    BufferedReader reader;

    Phone phone;
    PhoneRequest phoneRequest;
    PhoneResponse phoneResponse;

    @BeforeEach
    void setUp() throws Exception {
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
        writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        reader = Mockito.mock(BufferedReader.class);
        when(request.getReader()).thenReturn(reader);

        Field field = servlet.getClass().getDeclaredField("phoneService");
        field.setAccessible(true);
        field.set(servlet, service);
        servlet.getClass().getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class).setAccessible(true);
    }

    @Test
    @DisplayName("Проверка отправки GET запроса без параметра, должны получить список с информацией о всех телефонах в БД")
    void doGetWithoutParameter() throws Exception {
        when(request.getQueryString()).thenReturn(null);
        servlet.doGet(request, response);
        verify(writer, times(1)).print(any(String.class));
        verify(writer, times(1)).flush();
        verify(service, times(1)).getAllPhones();
    }

    @Test
    @DisplayName("Проверка отправки GET запроса c параметром id, должны получить информацию о телефоне по его id в БД")
    void doGetWithParameter() throws Exception {
        when(request.getQueryString()).thenReturn("id=1");
        when(request.getParameter("id")).thenReturn("1");
        when(service.getPhoneById(phone.getId())).thenReturn(phoneResponse);

        servlet.doGet(request, response);

        verify(writer, times(1)).print(any(String.class));
        verify(writer, times(1)).flush();
        verify(service, times(1)).getPhoneById(phone.getId());
    }

    @Test
    @DisplayName("Проверка отправки POST запроса, не должно быть выброшено никаких исключений")
    void doPostPositiveTest() throws Exception {
        ObjectMapper mapper = mock(ObjectMapper.class);
        Field field = PhoneServlet.class.getDeclaredField("objectMapper");
        field.setAccessible(true);
        field.set(servlet, mapper);

        when(mapper.writeValueAsString(any(PhoneResponse.class))).thenReturn(String.valueOf(phoneResponse));
        when(mapper.readValue(reader, PhoneRequest.class)).thenReturn(phoneRequest);
        when(request.getContentType()).thenReturn("application/json");
        when(service.create(phoneRequest)).thenReturn(phoneResponse);

        servlet.doPost(request, response);

        verify(writer, times(1)).print(any(String.class));
        verify(writer, times(1)).flush();
        verify(service, times(1)).create(phoneRequest);
        verify(mapper, times(1)).writeValueAsString(any(PhoneResponse.class));
        verify(mapper, times(1)).readValue(reader, PhoneRequest.class);
    }

    @Test
    @DisplayName("Проверка отправки POST запроса, неверный тип входного запроса 'text/html' вместо 'application/json'")
    void doPostNegativeTest() throws Exception {
        when(request.getContentType()).thenReturn("text/html");
        servlet.doPost(request, response);
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,"Invalid content type: text/html");
    }

    @Test
    @DisplayName("Проверка отправки PUT запроса, не должно быть выброшено никаких исключений")
    void doPutPositiveTest() throws Exception {
        ObjectMapper mapper = mock(ObjectMapper.class);
        Field field = PhoneServlet.class.getDeclaredField("objectMapper");
        field.setAccessible(true);
        field.set(servlet, mapper);

        when(mapper.writeValueAsString(any(PhoneResponse.class))).thenReturn(String.valueOf(phoneResponse));
        when(mapper.readValue(reader, PhoneRequest.class)).thenReturn(phoneRequest);
        when(request.getContentType()).thenReturn("application/json");
        when(request.getParameter("id")).thenReturn("1");
        when(service.update(phone.getId(), phoneRequest)).thenReturn(phoneResponse);

        servlet.doPut(request, response);

        verify(writer, times(1)).print(any(String.class));
        verify(writer, times(1)).flush();
        verify(service, times(1)).update(phone.getId(), phoneRequest);
        verify(mapper, times(1)).writeValueAsString(any(PhoneResponse.class));
        verify(mapper, times(1)).readValue(reader, PhoneRequest.class);
    }

    @Test
    @DisplayName("Проверка отправки PUT запроса, неверный тип входного запроса 'text/html' вместо 'application/json'")
    void doPutNegativeTest() throws Exception {
        when(request.getContentType()).thenReturn("text/html");
        servlet.doPut(request, response);
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,"Invalid content type: text/html");
    }

    @Test
    @DisplayName("Проверка отправки DELETE запроса, не должно быть выброшено никаких исключений")
    void doDeletePositiveTest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(service.delete(phone.getId())).thenReturn(phoneResponse);
        servlet.doDelete(request, response);
        verify(request, times(1)).getParameter("id");
        verify(service, times(1)).delete(phone.getId());
        verify(writer, times(1)).print(any(String.class));
        verify(writer, times(1)).flush();
    }

}
