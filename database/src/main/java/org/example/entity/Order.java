package org.example.entity;

import lombok.*;

import jakarta.persistence.*;

@Data
@EqualsAndHashCode(of = {"product", "user"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private User user;

    public void setProduct(Product product) {
        this.product = product;
        product.getOrders().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }
}
