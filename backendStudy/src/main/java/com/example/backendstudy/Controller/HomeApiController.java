package com.example.backendstudy.Controller;

import com.example.backendstudy.Model.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/api/home")
public class HomeApiController {

    private HashMap<Integer, String> userDataList;

    public HomeApiController(){
        userDataList = new HashMap<>();

        userDataList.put(1, "퀀타");
        userDataList.put(2, "매트릭스");
        userDataList.put(3, "정현우");
    }

    @GetMapping("/get-name") // 소문자로 작성
    // query
    public ResponseEntity<String> GetName(@RequestParam("id") Integer id) {
        if (userDataList.containsKey(id)) {
            return new ResponseEntity<>(userDataList.get(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // http://localhost:8080/api/home/get-name-by-id/1
    // path variable
    @GetMapping("/get-name-by-id/{id}")
    public ResponseEntity<String> GetNameById(@PathVariable("id") Integer id)
    {
        if (userDataList.containsKey(id)) {
            return new ResponseEntity<>(userDataList.get(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get : 조회 할 때 (Select)
    // Post : create 또는 보안 관련된 값을 보낼 때 (Insert) 많은 양을 담을 수 있다.
    // Put : Update 혹은 설정 변경 (Update) 많은 양을 담을 수 있다.
    // Delete : 삭제 (Delete)

    @PostMapping("/create")
    public ResponseEntity<Void> Create(@RequestBody CreateUserRequest param)
    {
        if(userDataList.containsKey(param.id))
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else
        {
            // Post : create 또는 보안 관련된 값을 보낼 때 (Insert) 많은 양을 담을 수 있다.
            userDataList.put(param.id, param.name);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @PutMapping("/update-name-by-id/{id}")
    public ResponseEntity<Void> Update(@PathVariable("id") Integer id, @RequestBody String newName)
    {
       if(!userDataList.containsKey(id))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            userDataList.put(id, newName);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> Delete(@PathVariable("id") Integer id)
    {
        if(!userDataList.containsKey(id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            userDataList.remove(id);
            return  new ResponseEntity<Void>(HttpStatus.OK);
        }
    }
}
