package com.web.letscoffee.domain;

import com.web.letscoffee.exception.Over10FavoriteException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="users")
@Getter
@Setter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false)
    private String name; // 로그인한 사용자 이름

    @Column(nullable = false)
    private String email; // 로그인한 사용자 이메일

    @Column
    private String picture;

    @Column
    private int favoriteCount; //즐겨찾기 10개로 제한할 예정입니다.

    @Column
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 권한

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> Favorites = new ArrayList<>();


    //==연관관계 매서드==//
    public void addFavorite(Favorite favorite) {
        Favorites.add(favorite);
        favorite.setUser(this);
    }

    //==생성 메서드==//

    //==비즈니스 로직==//
    /**
     * 즐겨찾기 추가
     */
    public void addFavoriteCount() {
        if(this.favoriteCount >= 10) {
            throw new Over10FavoriteException("즐겨찾기는 최대 10개까지 가능합니다");
        }
        ++this.favoriteCount;
    }
    /**
     * 즐겨찾기 취소
     */
    public void cancelFavoriteCount() {
        --this.favoriteCount;
    }


    //==조회 로직==//


    @Builder
    public Users(String name, String email, String picture, Role role, String provider, int favoriteCount) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
        this.favoriteCount = favoriteCount;
    }

    public Users update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

}
