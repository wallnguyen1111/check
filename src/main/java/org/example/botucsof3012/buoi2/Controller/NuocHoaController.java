package org.example.botucsof3012.buoi2.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.botucsof3012.buoi2.Repository.NuocHoaRepository;
import org.example.botucsof3012.buoi2.model.NuocHoa;

import java.io.IOException;

@WebServlet(name = "nuochoaController", value = {
        "/buoi2/hien-thi", //Get
        "/buoi2/them", //Post
        "/buoi2/chi-tiet", //Get
        "/buoi2/view-update", //Get -- hien thi form sua cho nguoi dung
        "/buoi2/update", //Post -- sua bang ghi
        "/buoi2/xoa",

}) // get
public class NuocHoaController extends HttpServlet {
    NuocHoaRepository nhRepo = new NuocHoaRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/buoi2/hien-thi")) {
            hienthi(req, resp);
        } else if (uri.contains("chi-tiet")) {
            chiTiet(req, resp);
        } else if (uri.contains("view-update")) {
            viewUpdate(req, resp);
        }else if (uri.contains("xoa")) {
            xoaNuocHoa(req,resp);
        }
    }

    private void xoaNuocHoa(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        nhRepo.xoa(id);
        resp.sendRedirect("/buoi2/hien-thi");
    }

    private void viewUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("nuocHoa", nhRepo.chiTiet(id));
        req.getRequestDispatcher("/buoi2/viewUpdate.jsp").forward(req, resp);
    }

    private void chiTiet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("nuocHoa", nhRepo.chiTiet(id));
        req.getRequestDispatcher("/buoi2/chiTiet.jsp").forward(req, resp);
    }

    private void hienthi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("danhSach", nhRepo.getAll());
        req.getRequestDispatcher("/buoi2/hienThi.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("them")) {
            themNuocHoa(req, resp);
        } else if (uri.contains("update")) {
            updateNuocHoa(req,resp);
        }
    }

    private void updateNuocHoa(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { // giong voi them
        Integer id = Integer.parseInt(req.getParameter("id"));
        String ten = req.getParameter("ten");
        Integer soLuong = Integer.valueOf(req.getParameter("soLuong"));
        Float gia = Float.valueOf(req.getParameter("gia"));

        NuocHoa nh = new NuocHoa(id, ten, soLuong, gia);
        nhRepo.update(nh);
        resp.sendRedirect("/buoi2/hien-thi");
    }

    private void themNuocHoa(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String ten = req.getParameter("ten");
        Integer soLuong = Integer.valueOf(req.getParameter("soLuong"));
        Float gia = Float.valueOf(req.getParameter("gia"));
        NuocHoa nh = new NuocHoa(null, ten, soLuong, gia);
        nhRepo.them(nh);
        resp.sendRedirect("/buoi2/hien-thi");
    }




}
