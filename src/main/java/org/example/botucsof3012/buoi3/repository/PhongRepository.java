package org.example.botucsof3012.buoi3.repository;

import org.example.botucsof3012.buoi3.util.HibernateConfig;
import org.example.botucsof3012.buoi3.model.Phong;
import org.hibernate.Session;

import java.util.List;

public class PhongRepository {
    private Session session;
    public PhongRepository() {
        session = HibernateConfig.getFACTORY().openSession();
    }

    public List<Phong> getAllPhong() {
        return session.createQuery("FROM Phong").list();
    }


    public Phong getPhong(int id) {
        return session.find(Phong.class, id);
    }

    public void themPhong(Phong phong) {
        try {
            session.beginTransaction();
            session.save(phong);
            session.getTransaction().commit();
        }catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void suaPhong(Phong phong) {
        try {
            session.beginTransaction();
            session.merge(phong);
            session.getTransaction().commit();
        }catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void xoaPhong(Integer id) {
        try {
            session.beginTransaction();
            session.delete(this.getPhong(id));
            session.getTransaction().commit();
        }catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
