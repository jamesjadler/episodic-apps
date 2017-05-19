package com.example.shows;


import org.springframework.data.repository.CrudRepository;

public interface ShowsRepo extends CrudRepository<Show,Long>{
         Show findByName(String show);
}
