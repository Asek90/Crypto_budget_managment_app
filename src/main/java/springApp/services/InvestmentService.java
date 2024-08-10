package springApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springApp.data.InvestmentRepository;
import springApp.data.UserRepository;
import springApp.model.Investment;
import springApp.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private UserRepository userRepository;

    public Investment createInvestment(Investment investment) {
        return investmentRepository.save(investment);
    }

    public List<Investment> getInvestmentsByUser(Optional<User> user) {
        return investmentRepository.findByUser(user);
    }

    public Investment getInvestmentById(Long id) {
        return investmentRepository.findById(id).orElse(null);
    }

    public void deleteInvestment(Investment investment) {
        investmentRepository.delete(investment);
    }

}
