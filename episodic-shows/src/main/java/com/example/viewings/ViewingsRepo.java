package com.example.viewings;

import com.example.shows.Show;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViewingsRepo extends CrudRepository<Viewing,Long>{
    Viewing findByUserId(Long id);
    List<Viewing> findByUserIdOrderByUpdatedAt(Long id);
}
