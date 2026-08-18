package an_paper.service;

import an_paper.dto.CartItem;
import an_paper.entity.Product;
import an_paper.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CartService {
    @Autowired private ProductRepository productRepository;

    @SuppressWarnings("unchecked")
    private Map<Long, CartItem> getCart(HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("CART");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("CART", cart);
        }
        return cart;
    }

    public void addToCart(Long productId, int quantity, HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (cart.containsKey(productId)) {
            CartItem item = cart.get(productId);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cart.put(productId, new CartItem(product, quantity));
        }
    }

    public void removeFromCart(Long productId, HttpSession session) {
        getCart(session).remove(productId);
    }

    public Collection<CartItem> getCartItems(HttpSession session) {
        return getCart(session).values();
    }

    public BigDecimal getTotalAmount(HttpSession session) {
        return getCartItems(session).stream()
            .map(CartItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute("CART");
    }
}