package ru.sber.mobile_phone_shop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.sber.mobile_phone_shop.datasource.PostgresConnectionManager;
import ru.sber.mobile_phone_shop.repository.impl.PhoneRepositoryImpl;
import ru.sber.mobile_phone_shop.repository.mapper.impl.PhoneResultMapperImpl;
import ru.sber.mobile_phone_shop.service.PhoneService;
import ru.sber.mobile_phone_shop.service.impl.PhoneServiceImpl;
import ru.sber.mobile_phone_shop.servlet.mapper.PhoneMapper;

import java.io.IOException;

@WebServlet("/api/v1/mobile-phones")
public class PhoneServlet extends HttpServlet {

    private final PhoneService phoneService = new PhoneServiceImpl(
            new PhoneRepositoryImpl(
                    new PostgresConnectionManager(),
                    new PhoneResultMapperImpl()),
            new PhoneMapper()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
