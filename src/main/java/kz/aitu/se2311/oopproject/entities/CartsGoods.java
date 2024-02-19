package kz.aitu.se2311.oopproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;

@Entity
@Table(name = "carts_goods")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartsGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "good_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private Good good;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "added_at")
    private Date added_at;

    @Column(name = "deleted")
    private Boolean isDeleted;

    @PrePersist
    private void init() {
        if (added_at == null) added_at = new Date();
        if (isDeleted == null) isDeleted = false;
    }

}
