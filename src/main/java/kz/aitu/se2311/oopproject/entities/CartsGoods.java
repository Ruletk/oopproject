package kz.aitu.se2311.oopproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "good_id", nullable = false)
    private Good good;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "added_at")
    private Date added_at = new Date();

}
