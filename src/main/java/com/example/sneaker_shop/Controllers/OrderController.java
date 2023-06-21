package com.example.sneaker_shop.Controllers;

import com.example.sneaker_shop.Services.CartService;
import com.example.sneaker_shop.Services.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private CartService cartService;

    @GetMapping("/submit")
    public String submitOrder(@RequestParam("amount") int amount,
                              HttpServletRequest request,
                              HttpSession session) throws UnsupportedEncodingException {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, amount, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/return-status")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1) {
            cartService.saveCart(request.getSession());
            return "order/complete";
        }
        else {
            return "order/fail";
        }
    }
}
