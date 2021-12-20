package at.flo.springauth.controller;


import at.flo.springauth.domain.Order;
import at.flo.springauth.domain.User;
import at.flo.springauth.payload.request.OrderRequest;
import at.flo.springauth.payload.update.UserCredentials;
import at.flo.springauth.repository.OrderRepository;
import at.flo.springauth.repository.UserRepository;
import at.flo.springauth.security.services.UserDetailsImpl;
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
    private final UserRepository userRepository;

    public DataController(OrderRepository orderRepository, UserRepository userRepository) {
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public String postOrder(@RequestBody OrderRequest orderRequest) {
        var user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var order = new Order(orderRequest.getName(), orderRequest.getAmount());

        var optionalUserInDatabase = userRepository.findByUsername(user.getUsername());
        if (optionalUserInDatabase.isPresent()) {
            var userInDatabase = optionalUserInDatabase.get();
            userInDatabase.addOrder(order);
            orderRepository.save(order);
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

    @PostMapping("/change")
    @PreAuthorize("hasRole('USER')")
    public void updateUser(@RequestBody UserCredentials newUser) {
        var user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        update(new User(user.getId(), newUser.getUsername(), newUser.getEmail()));
    }
    private void update(User user) {
        userRepository.update(user.getId(), user.getEmail(), user.getUsername());
    }
}