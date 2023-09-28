package com.example.backendstudy.Controller;

import com.example.backendstudy.Model.CreateUserRequest;
import com.example.backendstudy.entity.User;
import com.example.backendstudy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeApiController {

    private final UserService userService;

    @GetMapping("/get-name") // 소문자로 작성
    // query
    public ResponseEntity<String> GetName(@RequestParam("id") Long id) {
        try
        {
            if(id == null)
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else
            {
                Optional<User> result = userService.GetUserById(id);
                if(result.isPresent())
                {
                    User user =result.get();
                    String name = user.getName();
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // http://localhost:8080/api/home/get-name-by-id/1
    // path variable
    @GetMapping("/get-name-by-id/{id}")
    public ResponseEntity<String> GetNameById(@PathVariable("id") Long id)
    {
        try
        {
            if(id == null)
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else
            {
                Optional<User> result = userService.GetUserById(id);
                if(result.isPresent())
                {
                    User user =result.get();
                    String name = user.getName();
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get : 조회 할 때 (Select)
    // Post : create 또는 보안 관련된 값을 보낼 때 (Insert) 많은 양을 담을 수 있다.
    // Put : Update 혹은 설정 변경 (Update) 많은 양을 담을 수 있다.
    // Delete : 삭제 (Delete)

    @PostMapping("/create")
    public ResponseEntity<User> Create(@RequestBody CreateUserRequest param)
    {
        try
        {
            if(param == null || param.age == null || param.name == null || param.name.isBlank()){
                return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else {
                User newUser = userService.CreateUser(param.name, param.age);
                return new ResponseEntity<>(newUser, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-name-by-id/{id}")
    public ResponseEntity<Void> Update(@PathVariable("id") Long id, @RequestBody String newName)
    {
        try
        {
            if(id == null || newName == null || newName.isBlank())
            {
                return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else{
                boolean isDone = userService.UpdateUserNameById(id, newName);
                if(isDone){
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> Delete(@PathVariable("id") Long id)
    {
        try
        {
            if(id == null)
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else{
                userService.DeleteUserById(id);
                return  new ResponseEntity<>(HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
