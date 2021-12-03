package fi.digitalentconsulting.products.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.digitalentconsulting.products.entity.Store;
import fi.digitalentconsulting.products.repository.StoreRepository;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {
	private static Logger LOGGER = LoggerFactory.getLogger(StoreController.class);
	
	private StoreRepository storeRepository;
	
	
	public StoreController(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}


	@GetMapping()
	public ResponseEntity<?> stores() {
		return ResponseEntity.ok(storeRepository.findAll());
	}

	@GetMapping("/{id}/products")
	public ResponseEntity<?> inventory(@PathVariable Long id) {
		Optional<Store> inventoryStore = storeRepository.findInventoryForStore(new Store(id));
		return ResponseEntity.ok(inventoryStore.orElseThrow().getInventory());
	}
}
