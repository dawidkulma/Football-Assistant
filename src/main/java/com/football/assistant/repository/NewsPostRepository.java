package com.football.assistant.repository;

import com.football.assistant.domain.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsPostRepository extends JpaRepository<NewsPost,Integer> {
}
