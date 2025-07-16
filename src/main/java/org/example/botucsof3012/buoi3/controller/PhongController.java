package org.example.botucsof3012.buoi3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.botucsof3012.buoi3.model.Phong;
import org.example.botucsof3012.buoi3.repository.PhongRepository;

import java.io.IOException;

@WebServlet(name = "phongController" , value = {
        "/Buoi3/hien-thi",
        "/Buoi3/chi-tiet",
        "/Buoi3/xoa",
        "/Buoi3/them",
        "/Buoi3/view-update",
        "/Buoi3/update"

})
public class PhongController extends HttpServlet {
    PhongRepository phongRepository = new PhongRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("hien-thi")) {
            hienThi(req,resp);
        }
    }

    private void hienThi(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        req.setAttribute("listPhong",phongRepository.getAllPhong());
        req.getRequestDispatcher("/buoi3/hienThi.jsp").forward(req, resp);
    }
}
