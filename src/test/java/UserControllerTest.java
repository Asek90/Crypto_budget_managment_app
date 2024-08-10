import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springApp.model.Investment;
import springApp.model.User;
import springApp.services.InvestmentService;
import springApp.services.UserService;
import springApp.web.InvestmentsController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private InvestmentService investmentService;

    @Mock
    private UserService userService;

    @InjectMocks
    private InvestmentsController investmentController;

    private User testUser;
    private Investment testInvestment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User( "testUser", "password", "ROLE_USER");

        testInvestment = new Investment(
                testUser,
                "Bitcoin",
                0.5,
                20000.0,
                LocalDateTime.now()
        );
    }

    @Test
    public void testGetInvestmentsByUser_UserExistsAndHasInvestments_ReturnsInvestments() {

        when(userService.getUserById(1L)).thenReturn(testUser);
        when(investmentService.getInvestmentsByUser(Optional.ofNullable(testUser))).thenReturn(List.of(testInvestment));

        ResponseEntity<List<Investment>> response = investmentController.getInvestmentsByUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(testInvestment, response.getBody().get(0));
    }


    @Test
    public void testGetInvestmentsByUser_UserExistsButNoInvestments_ReturnsNoContent() {

        when(userService.getUserById(1L)).thenReturn(testUser);
        when(investmentService.getInvestmentsByUser(Optional.ofNullable(testUser))).thenReturn(Collections.emptyList());

        ResponseEntity<List<Investment>> response = investmentController.getInvestmentsByUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetInvestmentsByUser_UserDoesNotExist_ReturnsNotFound() {

        when(userService.getUserById(1L)).thenReturn(null);


        ResponseEntity<List<Investment>> response = investmentController.getInvestmentsByUser(1L);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());  // Sprawdź, czy status to 404 Not Found
        assertNull(response.getBody());  // Sprawdź, czy body jest nullem
    }

}
