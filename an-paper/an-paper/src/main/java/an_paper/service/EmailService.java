package an_paper.service;

import an_paper.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(Order order) {
        if (mailSender == null) return;
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(order.getCustomerEmail());
            helper.setSubject("A.N PAPER - Xác nhận đơn hàng #" + order.getId());

            String content = "<h2>Cảm ơn bạn đã đặt hàng tại A.N PAPER!</h2>"
                + "<p>Mã đơn hàng: <b>#" + order.getId() + "</b></p>"
                + "<p>Tổng tiền: <b>" + order.getTotalAmount() + " VNĐ</b></p>"
                + "<p>Trạng thái: <b>Chờ xác nhận & giao hàng</b></p>";

            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Lỗi gửi Email: " + e.getMessage());
        }
    }
}

