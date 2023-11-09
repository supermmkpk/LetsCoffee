package com.web.letscoffee.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column
    private String storeName;

    @Column
    private String wifipass;

    @Column
    private String toiletpass;

    @Column(length = 1200)
    private String otherInfo;

    //==생성 메서드==//
    public static Favorite createUserFavoriteStore(Users user, String storeName) {
        Favorite userFavorite = new Favorite();
        userFavorite.setUser(user);
        userFavorite.setStoreName(storeName);

        user.addFavorite(userFavorite);
        user.addFavoriteCount(); //즐겨찾기 추가했으니까 favoriteCount 증가

        return userFavorite;
    }

    public static Favorite createFavoriteInfo(Favorite favorite, String wifipass, String toiletpass, String otherInfo){
        favorite.setWifipass(wifipass);
        favorite.setToiletpass(toiletpass);
        favorite.setOtherInfo(otherInfo);

        return favorite;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getUser().cancelFavoriteCount();
    }

    //==조회 로직==//


}
