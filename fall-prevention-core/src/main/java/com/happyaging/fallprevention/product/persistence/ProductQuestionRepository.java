package com.happyaging.fallprevention.product.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.product.entity.ProductQuestion;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {
    List<ProductQuestion> findAllByQuestionId(Long questionId);

}
