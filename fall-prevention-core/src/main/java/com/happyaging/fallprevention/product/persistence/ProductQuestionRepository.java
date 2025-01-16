package com.happyaging.fallprevention.product.persistence;

import com.happyaging.fallprevention.product.entity.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {
    List<ProductQuestion> findAllByQuestionId(Long questionId);

}
