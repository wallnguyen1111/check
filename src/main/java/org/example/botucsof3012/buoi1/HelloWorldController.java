package org.example.botucsof3012.buoi1;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloWorldController", value = {
        "/buoi1/hello",
        "/buoi1/hien-thi",
        "/buoi1/dang-nhap"
})
public class HelloWorldController extends HttpServlet {
    // gõ doGet
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        System.out.println(uri);
        if (uri.contains("hello")) {
            helloworld(req, resp);
        }if (uri.contains("hien-thi")) {
            hienthi(req,resp);
        }
    }



    private void hienthi(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        req.getRequestDispatcher("/buoi1/hienThi.jsp").forward(req, resp);
    }

    private void helloworld(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("ten", "Nguyen Van A");
        req.getRequestDispatcher("/buoi1/helloworld.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("dang-nhap")) {
            dangnhap(req,resp);
        }
    }

    private void dangnhap(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username"); // lay username tu form dang nhap
        String pass = req.getParameter("password"); // lay password tu form dăng nhập
        req.setAttribute("username", user); // dat gia tri lay duoc tu form roi truyen vao jsp
        req.setAttribute("password", pass);
        req.getRequestDispatcher("/buoi1/thongTin.jsp").forward(req,resp);
    }
}
