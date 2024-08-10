package springApp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springApp.model.Investment;
import springApp.model.User;
import springApp.services.InvestmentService;
import springApp.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/investments")
public class InvestmentsController {

    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Investment> createInvestment(@RequestBody Investment investment) {
        Investment createdInvestment = investmentService.createInvestment(investment);
        return new ResponseEntity<>(createdInvestment, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Investment>> getInvestmentsByUser(@PathVariable Long userId) {

        User user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Investment> investmentsByUser = investmentService.getInvestmentsByUser(Optional.of(user));

        if (investmentsByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(investmentsByUser);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        Investment investmentById = investmentService.getInvestmentById(id);
        if (investmentById != null) {
            investmentService.deleteInvestment(investmentById);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

