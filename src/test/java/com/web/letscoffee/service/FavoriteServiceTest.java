package com.web.letscoffee.service;

import com.web.letscoffee.domain.Favorite;
import com.web.letscoffee.domain.Role;
import com.web.letscoffee.domain.Users;
import com.web.letscoffee.exception.Over10FavoriteException;
import com.web.letscoffee.repository.FavoriteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FavoriteServiceTest {
    @Autowired
    EntityManager em;

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    FavoriteRepository favoriteRepository;


    @Test
    public void 즐겨찾기_추가() throws Exception {
        //given
        Users user = createUser();

        //when
        Long favoriteId = favoriteService.addFavorite(user.getId(), "스타벅스 홍대");

        //then
        Favorite getFavorite = favoriteRepository.findOne(favoriteId);


        assertEquals("즐겨찾기 추가 시 user의 favoriteCount가 증가해야 한다", 1, user.getFavoriteCount());
        assertEquals("즐겨찾기 개수가 정확해야 한다", 1, user.getFavorites().size());
        assertEquals("매장 이름이 정확해야 한다", "스타벅스 홍대", getFavorite.getStoreName());
        assertEquals("등록한 user가 정확해야 한다.", user, getFavorite.getUser());
    }

    @Test(expected = Over10FavoriteException.class)
    public void 즐겨찾기_추가_10개초과() throws Exception {
        //given
        Users user = createUser();
        user.setFavoriteCount(10);

        //when
        favoriteService.addFavorite(user.getId(), "스타벅스 홍대");

        //then
        fail("즐겨찾기 개수 예외가 발생해야 한다.");
    }

    @Test
    public void 즐겨찾기_취소() throws Exception {
        //given
        Users user = createUser(); //favoriteCount : 0
        Long favoriteId = favoriteService.addFavorite(user.getId(), "스타벅스 홍대"); //favoriteCount : 1
        Long favoriteId2 = favoriteService.addFavorite(user.getId(), "커피빈 상수"); //favoriteCount : 2

        //when
        favoriteService.cancelFavorite(favoriteId); //favoriteCount : 1

        //then
        Favorite getFavorite = favoriteRepository.findOne(favoriteId);

        assertEquals("즐겨찾기 추가 시 user의 favoriteCount가 감소해야 한다", 1, user.getFavoriteCount());
        assertNull("해당 즐겨찾기가 없어야 한다", getFavorite);
    }

    @Test
    public void 즐겨찾기_메모() throws Exception {
        //given
        Users user = createUser(); //favoriteCount : 0
        Long favoriteId = favoriteService.addFavorite(user.getId(), "스타벅스 홍대");

        //when
        favoriteService.addFavoriteInfo(favoriteId,"1234", "5678*", "영업시간은 23시까지");

        //then
        Favorite getFavorite = favoriteRepository.findOne(favoriteId);
        assertEquals("와이파이 정보가 정확해야 한다", "1234", getFavorite.getWifipass());
        assertEquals("화장실 정보가 정확해야 한다", "5678*", getFavorite.getToiletpass());
        assertEquals("기타 정보가 정확해야 한다", "영업시간은 23시까지", getFavorite.getOtherInfo());
    }

    private Users createUser() {
        Users user = Users.builder()
                .name("회원1")
                .email("abc@gmail.com")
                .picture("picture")
                .favoriteCount(0)
                .provider("google")
                .role(Role.USER)
                .build();
        em.persist(user);


        return user;
    }
}
