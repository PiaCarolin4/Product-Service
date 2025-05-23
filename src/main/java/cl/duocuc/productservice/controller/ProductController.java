package cl.duocuc.productservice.controller;

import cl.duocuc.productservice.controller.response.MessageResponse;
import cl.duocuc.productservice.service.ProductService;
import cl.duocuc.productservice.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    List<Product> products;

    public ProductController() {
        products = ProductService.findAll();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return ResponseEntity.ok(product);
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<MessageResponse> createProduct(@RequestBody Product request) {
        String id = request.getId();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new MessageResponse("Error: Producto existente"));
            }
        }
        products.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                products.remove(product);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new MessageResponse("Producto eliminado"));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity <MessageResponse> replaceProduct(
            @PathVariable String id,
            @RequestBody Product request){
        int count = 0;
        for (Product product : products) {
            if (product.getId().equals(id)) {
                products.set(count, request);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            count++;
        }
        return ResponseEntity.notFound().build();
    }




}
