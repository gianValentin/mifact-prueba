package pe.giancarlo.mifactPrueba.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.giancarlo.mifactPrueba.dto.request.ProductCreateRequest;
import pe.giancarlo.mifactPrueba.dto.request.ProductUpdateRequest;
import pe.giancarlo.mifactPrueba.dto.response.PageResponse;
import pe.giancarlo.mifactPrueba.dto.response.ProductResponse;
import pe.giancarlo.mifactPrueba.entity.Product;
import pe.giancarlo.mifactPrueba.exception.ProductNotFoundException;
import pe.giancarlo.mifactPrueba.repository.ProductRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final Set<String> SORT_FIELDS = Set.of("idProduct", "name", "description", "count", "price");
    private static final String DEFAULT_SORT = "idProduct";

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> list(String q,
                                              int page,
                                              int size,
                                              String sort,
                                              String direction
    ) {
        String text = (q == null || q.isBlank()) ? null : q.trim();
        String field = (sort == null || sort.isBlank() || !SORT_FIELDS.contains(sort)) ? DEFAULT_SORT : sort;
        Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        int sizeRound = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(Math.max(page, 0), sizeRound, Sort.by(dir, field));

        Page<Product> resultado = productRepository.buscar(text, pageable);
        List<ProductResponse> contenido = resultado.getContent().stream().map(this::toResponse).toList();

        return new PageResponse<>(
                contenido,
                resultado.getNumber(),
                resultado.getSize(),
                resultado.getTotalElements(),
                resultado.getTotalPages(),
                resultado.isFirst(),
                resultado.isLast());
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCount(request.getCount());
        product.setPrice(request.getPrice());

        return toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = searchOrTrow(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCount(request.getCount());
        product.setPrice(request.getPrice());

        return toResponse(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return toResponse(searchOrTrow(id));
    }

    @Transactional
    public void delete(Long id) {
        searchOrTrow(id);
        productRepository.deleteById(id);
    }

    private Product searchOrTrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductResponse toResponse(Product u) {
        return new ProductResponse(
                u.getIdProduct(),
                u.getName(),
                u.getDescription(),
                u.getCount(),
                u.getPrice());
    }

}
