package com.graduate2.project.domain;

import com.graduate2.project.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Favorite favorite;

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setFavorite(Favorite favorite)
    {
        this.favorite = favorite;
    }
}
