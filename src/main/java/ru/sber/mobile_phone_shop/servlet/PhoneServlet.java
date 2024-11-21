package ru.sber.mobile_phone_shop.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sber.mobile_phone_shop.datasource.PostgresConnectionManager;
import ru.sber.mobile_phone_shop.repository.impl.PhoneRepositoryImpl;
import ru.sber.mobile_phone_shop.repository.mapper.impl.PhoneResultMapperImpl;
import ru.sber.mobile_phone_shop.service.PhoneService;
import ru.sber.mobile_phone_shop.service.impl.PhoneServiceImpl;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneRequest;
import ru.sber.mobile_phone_shop.servlet.dto.PhoneResponse;
import ru.sber.mobile_phone_shop.servlet.mapper.PhoneMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/v1/mobile-phones")
public class PhoneServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(PhoneServlet.class);
    private final PhoneService phoneService = new PhoneServiceImpl(
            new PhoneRepositoryImpl(
                    new PostgresConnectionManager(),
                    new PhoneResultMapperImpl()),
            new PhoneMapper()
    );
    private final ObjectMapper objectMapper = new JsonMapper();
    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "application/json";
    private static final String INVALID_CONTENT_TYPE_ERR_MSG = "Invalid content type";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING);
        try (PrintWriter writer = resp.getWriter()) {
            if (req.getQueryString() == null) {
                writer.print(
                        objectMapper.writeValueAsString(phoneService.getAllPhones()));
            } else {
                Long id = Long.valueOf(req.getParameter("id"));
                writer.print(objectMapper.writeValueAsString(phoneService.getPhoneById(id)));
            }
            writer.flush();
        } catch (IllegalArgumentException e) {
            try {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } catch (IOException i) {
                log.log(Level.ERROR, i.getMessage());
            }
        } catch (IOException e) {
            log.log(Level.ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING);

        try (PrintWriter writer = resp.getWriter();
             BufferedReader reader = req.getReader()) {
            String reqContentType = req.getContentType();
            if (!CONTENT_TYPE.equals(reqContentType)) {
                log.log(Level.ERROR, INVALID_CONTENT_TYPE_ERR_MSG);
                resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, INVALID_CONTENT_TYPE_ERR_MSG);
            }
            PhoneRequest request = objectMapper.readValue(reader, PhoneRequest.class);
            PhoneResponse response = phoneService.create(request);
            writer.print(objectMapper.writeValueAsString(response));
            writer.flush();
        } catch (IllegalArgumentException e) {
            try {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } catch (IOException i) {
                log.log(Level.ERROR, i.getMessage());
            }
        } catch (IOException e) {
            log.log(Level.ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING);

        try (PrintWriter writer = resp.getWriter();
             BufferedReader reader = req.getReader()) {
            String reqContentType = req.getContentType();
            if (!CONTENT_TYPE.equals(reqContentType)) {
                log.log(Level.ERROR, INVALID_CONTENT_TYPE_ERR_MSG);
                resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, INVALID_CONTENT_TYPE_ERR_MSG);
            }
            Long id = Long.valueOf(req.getParameter("id"));
            PhoneRequest request = objectMapper.readValue(reader, PhoneRequest.class);
            PhoneResponse response = phoneService.update(id, request);
            writer.print(objectMapper.writeValueAsString(response));
            writer.flush();
        } catch (IllegalArgumentException e) {
            try {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } catch (IOException i) {
                log.log(Level.ERROR, i.getMessage());
            }
        } catch (IOException e) {
            log.log(Level.ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING);

        try (PrintWriter writer = resp.getWriter()) {
            Long id = Long.valueOf(req.getParameter("id"));
            writer.print(objectMapper.writeValueAsString(phoneService.delete(id)));
            writer.flush();
        } catch (IllegalArgumentException e) {
            try {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } catch (IOException i) {
                log.log(Level.ERROR, i.getMessage());
            }
        } catch (IOException e) {
            log.log(Level.ERROR, e.getMessage());
        }
    }
}
