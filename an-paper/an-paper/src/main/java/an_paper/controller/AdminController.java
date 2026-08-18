package an_paper.controller;

import an_paper.entity.Order;
import an_paper.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ReviewRepository reviewRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("revenue", orderRepository.calculateTotalRevenue());
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("reviews", reviewRepository.findAll());
        return "admin-dashboard";
    }

    // ADMIN Bấm: ĐÃ GIAO HÀNG
    @PostMapping("/orders/{id}/ship")
    public String shipOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus("SHIPPED");
        orderRepository.save(order);
        return "redirect:/admin/dashboard";
    }

    // ADMIN Xóa Đánh giá vi phạm
    @PostMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}
