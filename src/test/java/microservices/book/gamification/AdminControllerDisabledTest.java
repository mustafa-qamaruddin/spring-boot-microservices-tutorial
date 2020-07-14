package microservices.book.gamification;

import static org.mockito.Mockito.verifyNoInteractions;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import microservices.book.gamification.controller.AdminController;
import org.junit.jupiter.api.Test;
import microservices.book.gamification.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;


@WebMvcTest(AdminController.class)
public class AdminControllerDisabledTest {
    
    @MockBean
    private AdminService adminService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void deleteDatabaseTest() throws Exception {
        
        MockHttpServletResponse response = mvc.perform(
            post("/gamification/admin/delete-db").accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verifyNoInteractions(adminService);
    }
}