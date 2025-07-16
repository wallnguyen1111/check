package org.example.botucsof3012.buoi2.Repository;

import org.example.botucsof3012.buoi2.Uni.HibernateConfig;
import org.example.botucsof3012.buoi2.model.NuocHoa;
import org.hibernate.Session;

import java.util.List;


public class NuocHoaRepository {
    private Session session = null; // ket noi voi co so giu lieu  , tao phien

    public NuocHoaRepository() {
        session = HibernateConfig.getFACTORY().openSession();
    }

    public List<NuocHoa> getAll() {
        return session.createQuery("from NuocHoa").list(); // hướng tới
    }

    public NuocHoa chiTiet(Integer id) {
        return session.find(NuocHoa.class, id);
    }

    public void them(NuocHoa nuocHoa) {
//        bắt đầu ở begin điểm cuối hoàn thành là commit
        try {
            session.beginTransaction();
            session.save(nuocHoa);
            session.getTransaction().commit();
//            nếu 1 trong 3 phần này lõi thì chạy rollback quay về begin
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void update(NuocHoa nuocHoa) {
//        bắt đầu ở begin điểm cuối hoàn thành là commit
        try {
            session.beginTransaction();
            session.merge(nuocHoa);
            session.getTransaction().commit();
//            nếu 1 trong 3 phần này lõi thì chạy rollback quay về begin
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void xoa(Integer id) {
//        bắt đầu ở begin điểm cuối hoàn thành là commit
        try {
            session.beginTransaction();
            session.delete(this.chiTiet(id)); // chi tiet giong nhu findone truyền vào id và trả ra 1 obj tương ứng
            session.getTransaction().commit();
//            nếu 1 trong 3 phần này lõi thì chạy rollback quay về begin
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
