package fi.digitalentconsulting.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fi.digitalentconsulting.products.entity.Store;

public interface StoreRepository extends PagingAndSortingRepository<Store, Long> {

	@Query("SELECT s from Store s WHERE s = :store")
	Optional<Store> findInventoryForStore(@Param("store") Store store);

}
