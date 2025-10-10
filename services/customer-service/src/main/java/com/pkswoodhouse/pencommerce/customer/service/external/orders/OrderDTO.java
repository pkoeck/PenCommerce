package com.pkswoodhouse.pencommerce.customer.ac.orders;

import java.util.List;

public record OrderDTO(Long id, long userId, OrderStatusEnum status, double depositAmount, double totalAmount, List<OrderItemDTO> orderItems) {
}
