package wblabs.io.btgpactual.orderms.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wblabs.io.btgpactual.orderms.controller.dto.ApiResponse;
import wblabs.io.btgpactual.orderms.controller.dto.OrderResponse;
import wblabs.io.btgpactual.orderms.controller.dto.PaginationResponse;
import wblabs.io.btgpactual.orderms.service.OrderService;

import java.util.Map;

@RestController
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> list(
            @PathVariable("customerId") Long customerId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        var response = service.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOnOrders = service.findTotalOnOrdersByCustomerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                response.getContent(),
                PaginationResponse.fromPage(response)
        ));
    }
}
