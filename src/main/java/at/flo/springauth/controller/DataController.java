package at.flo.springauth.controller;


import at.flo.springauth.domain.Order;
import at.flo.springauth.payload.request.OrderRequest;
import at.flo.springauth.repository.OrderRepository;
import at.flo.springauth.repository.UserRepository;
import at.flo.springauth.security.services.UserDetailsImpl;
import at.flo.springauth.security.services.UserDetailsServiceImpl;
import ch.qos.logback.classic.BasicConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class DataController {

    private final OrderRepository orderRepository;
    private final UserDetailsServiceImpl userRepository;

    public DataController(OrderRepository orderRepository, UserDetailsServiceImpl userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }


    @PostMapping("/test")
    public String postMe(@RequestBody String request) {
        var logger = LoggerFactory.getLogger(DataController.class);
        logger.info(request);
        return "hello world";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping("/order")
    @PreAuthorize("hasRole('ADMIN')")
    public String order() {
        var user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var orders = user.getOrders();
        return "Order for user " + orders.size();
    }



    @PostMapping("/placeorder")
    @PreAuthorize("hasRole('ADMIN')")
    public String postOrder(@RequestBody OrderRequest orderRequest) {
        var user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var order = new Order(orderRequest.getName(), orderRequest.getAmount());

        var optionalUserInDatabase = userRepository.findByUsername(user.getUsername());
        if (optionalUserInDatabase.isPresent()) {
            var userInDatabase = optionalUserInDatabase.get();
            userInDatabase.addOrder(order);
            orderRepository.save(order);
//            userRepository.save(userInDatabase);
            return "Order placed";
        } else
            return "User not found";
    }

    @GetMapping("/myorders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getOrdersForUser() {
        var principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ArrayList<>(principal.getOrders());
    }
}