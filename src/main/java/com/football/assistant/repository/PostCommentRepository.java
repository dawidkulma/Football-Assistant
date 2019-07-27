package com.football.assistant.repository;

import com.football.assistant.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment,Integer> {
}
