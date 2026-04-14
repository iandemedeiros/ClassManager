package ClassManager.app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private Integer ticket = 0;

    @Min(value = 0, message = "Default price must be a non-negative value")
    private Double defaultPrice = (double) 100;

    private Boolean status = true; // ACTIVE / INACTIVE

    // Relacionamento com User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
