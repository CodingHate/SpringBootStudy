package com.example.backendstudy.repository;

import com.example.backendstudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> getUsersByNameContains(String name);

    @Modifying // database에 수정을 한다는 것을 명시해준다.
    @Query("update User u set u.name=:newName where u.id=:id")
    int updateUserNameById(Long id, String newName); // return int 인 이유는 몇개가 update가 되었는지 반환해준다.

    @Modifying
    @Query("delete User u where u.id=:id")
    int deleteUserById(Long id);



//    List<User> getUsersByNameContains(String name);
//
//    @Modifying
//    @Query("update User u set u.name=:newName where u.id=:id")
//    int updateUserNameById(Long id, String newName);
//
//    @Modifying
//    @Query("delete User u where u.id=:id")
//    int deleteUserById(Long id);
}
