package org.example.botucsof3012.buoi3.repository;

import org.example.botucsof3012.buoi3.util.HibernateConfig;
import org.example.botucsof3012.buoi3.model.KhachSan;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class KhachSanRepository {

    private Session session;

    public KhachSanRepository() {
        session = HibernateConfig.getFACTORY().openSession();
    }

    public List<KhachSan> getAllKhachSan() {
        return session.createQuery("from KhachSan ").list();
    }

    //Them thong tin cua phong
    public KhachSan getKhachSan(int id) {
        return session.find(KhachSan.class, id);
    }
}
