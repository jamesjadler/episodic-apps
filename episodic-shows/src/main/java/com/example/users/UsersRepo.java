package com.example.users;


import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<User,Long>{
        User findUserByEmail(String name);
}
