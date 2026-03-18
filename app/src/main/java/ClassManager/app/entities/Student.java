package ClassManager.app.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "tb_student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 150)
    private String email;

    private Double defaultPrice;

    private Integer defaultDuration; // minutes

    @Column(nullable = false)
    private String status; // ACTIVE / INACTIVE

    @Column(length = 500)
    private String notes;

    // Relacionamento com User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}


