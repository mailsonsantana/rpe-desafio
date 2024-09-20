package br.com.ms.carrierservice.repository;

import br.com.ms.carrierservice.model.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrierRepository extends JpaRepository<Carrier, Long> {
}
