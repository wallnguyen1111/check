package org.example.botucsof3012.buoi2.model;

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
@Table(name = "nuoc_hoa")
public class NuocHoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id tu sinh ra
    private Integer id;
    @Column(name = "Ten")
    private String ten;
    @Column(name = "so_luong") // vd noi soluong voi bang trong sql server
    private Integer soLuong;
    @Column(name = "gia")
    private  Float gia;
}
