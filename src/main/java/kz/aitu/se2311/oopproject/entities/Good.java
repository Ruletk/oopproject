package kz.aitu.se2311.oopproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 128)
    private String name;

    @Column(length=128, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer price;

    private Date created_at = new Date();

    private Date updated_at = new Date();

    private Date deleted_at;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<CartsGoods> carts;


}