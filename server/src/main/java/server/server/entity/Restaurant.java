package server.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="restaurant" )
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;
    @Column(name="restaurantName")
    private String restaurantName;
    @Column(name="address")
    private String address;
    @OneToMany(mappedBy = "restaurant")
    private List<Comment> comments;
}
