package org.example.botucsof3012.buoi3.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phong")
public class Phong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_phong")
    private String tenPhong;

    @Column(name = "gia")
    private Integer gia;

    @Column(name = "con_trong")
    private Boolean conTrong;

//   đây là phần nối 2 model
    @ManyToOne
    @JoinColumn(name = "khach_san_id", referencedColumnName = "id")
    private KhachSan khachSan;


}
