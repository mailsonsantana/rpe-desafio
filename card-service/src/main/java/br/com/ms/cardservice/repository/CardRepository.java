package br.com.ms.cardservice.repository;

import br.com.ms.cardservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByCarrierId(Long carrierId);
}
