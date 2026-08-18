package an_paper.controller;

import an_paper.entity.*;
import an_paper.repository.*;
import an_paper.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {
    @Autowired private CartService cartService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private EmailService emailService;

    @PostMapping("/checkout")
    public String checkout(@RequestParam String address, @RequestParam String phone,
                           HttpSession session, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Order order = Order.builder()
            .user(user)
            .customerName(user.getFullName())
            .customerEmail(user.getEmail())
            .customerPhone(phone)
            .shippingAddress(address)
            .totalAmount(cartService.getTotalAmount(session))
            .status("PENDING")
            .build();

        cartService.getCartItems(session).forEach(item -> {
            OrderDetail detail = OrderDetail.builder()
                .order(order)
                .product(item.getProduct())
                .quantity(item.getQuantity())
                .price(item.getProduct().getPrice())
                .build();
            order.getOrderDetails().add(detail);
        });

        orderRepository.save(order);
        emailService.sendOrderConfirmation(order);
        cartService.clearCart(session);

        return "redirect:/user/orders";
    }

    @GetMapping("/user/orders")
    public String myOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("orders", orderRepository.findByUserId(user.getId()));
        return "my-orders";
    }

    // USER Bấm nút: ĐÃ NHẬN HÀNG -> Chuyển trạng thái PAID -> Thu tiền vào Doanh thu
    @PostMapping("/user/orders/{id}/confirm-received")
    public String confirmReceived(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        if ("SHIPPED".equals(order.getStatus())) {
            order.setStatus("PAID"); // Chuyển thành Đã thanh toán -> Cộng doanh thu
            orderRepository.save(order);
        }
        return "redirect:/user/my-orders";
    }
}

