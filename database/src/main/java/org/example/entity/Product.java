package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Data
@EqualsAndHashCode(of = {"productName", "user"})
@ToString(exclude = {"orders"})
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder
@Entity
@Table(name = "product",
        uniqueConstraints = @UniqueConstraint(columnNames = {"productName", "user_id"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 64)
    private String productName;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false, length = 800)
    private String description;
    @Column
    private String image;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();
}
