package com.example.viewings;

import com.example.shows.Show;
import org.springframework.data.repository.CrudRepository;

public interface ViewingsRepo extends CrudRepository<Viewing,Long>{
    Viewing findByUserId(Long id);
}
