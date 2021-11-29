package fi.digitalentconsulting.products.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import fi.digitalentconsulting.products.entity.Product;
import fi.digitalentconsulting.products.repository.ProductRepository;
import fi.digitalentconsulting.products.service.DatamuseService;
import fi.digitalentconsulting.products.service.WordServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	private final ProductRepository productRepository;
	private final DatamuseService datamuseService;

	public ProductController(ProductRepository productRepository, DatamuseService datamuseService) {
		this.productRepository = productRepository;
		this.datamuseService = datamuseService;
	}

	@Operation(summary = "List all products")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "List of products", 
			    content = { @Content(mediaType = "application/json", 
			    array = @ArraySchema(schema = @Schema(implementation = Product.class))) })
			})
	@GetMapping("")
	public ResponseEntity<List<Product>> products(@RequestParam(name="minPrice", required=false) Double minPrice,
			@RequestParam(name="search", required=false) String search) {
		if (minPrice != null && search != null) {
			return ResponseEntity.ok(productRepository.findAllByNameContainingIgnoreCaseAndPriceGreaterThan(search, minPrice));
		}
		if (minPrice != null) {
			return ResponseEntity.ok(productRepository.findAllByPriceGreaterThan(minPrice));
		} 
		if (search != null) {
			return ResponseEntity.ok(productRepository.findAllByNameContainingIgnoreCase(search));
		}
		return ResponseEntity.ok(productRepository.findAll());
	}
	
	@Operation(summary = "Get a single product")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Found product", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = Product.class)) }),
			  @ApiResponse(responseCode = "404", description = "Not found", 
			    content = @Content(schema=@Schema(implementation=ExceptionMessage.class)))})
	@GetMapping("/{id}")
	public ResponseEntity<Product> product(@Parameter(description="Product id") @PathVariable Long id) throws NoSuchElementException {
		Optional<Product> optProduct = productRepository.findById(id);
		return ResponseEntity.ok(optProduct.orElseThrow());
	}
	
	@Operation(summary = "Create a new product")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "201", description = "Created a product", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = Product.class)) }),
			  @ApiResponse(responseCode = "400", description = "A bad request", 
			    content = @Content(mediaType = "application/json"))})
	@PostMapping("")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		Product created = productRepository.save(product);
		URI location = ServletUriComponentsBuilder
	            .fromCurrentRequest().path("/{id}")
	            .buildAndExpand(created.getId()).toUri();
		return ResponseEntity.created(location).body(created);
	}
	
	@Operation(summary = "Get synonyms for a product name")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Synonyms, max 10", 
			    content = { @Content(mediaType = "application/json", 
			    		array = @ArraySchema(schema = @Schema(implementation = String.class))) }),
			  @ApiResponse(responseCode = "404", description = "Product not found", 
			    content = @Content(schema=@Schema(implementation=ExceptionMessage.class)))})
	@GetMapping("/{id}/synonyms")
	public ResponseEntity<List<String>> productNameSynonyms(@Parameter(description="Product id") @PathVariable Long id) throws NoSuchElementException {
		Optional<Product> optProduct = productRepository.findById(id);
		String name = optProduct.orElseThrow().getName();
		List<String> synonyms;
		try {
			synonyms = datamuseService.getSynonyms(name);
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			throw new WordServiceException("Problem with synonyms", e);
		}
		return ResponseEntity.ok(synonyms);
	}

}
