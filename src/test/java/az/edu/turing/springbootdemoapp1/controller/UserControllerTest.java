package az.edu.turing.springbootdemoapp1.controller;

import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import az.edu.turing.springbootdemoapp1.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static az.edu.turing.springbootdemoapp1.common.TestConstants.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void getAll_Should_ReturnSuccess() throws Exception {

        given(userService.getAll()).willReturn(Set.of(getUserDto()));
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(getUserDto()))));

        then(userService).should(times(1)).getAll();
    }

    @Test
    void getById_Should_ReturnSuccess() throws Exception {
        given(userService.getById(ID)).willReturn(getUserDto());

        mockMvc.perform(get("/api/v1/users/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getUserDto())));

        then(userService).should(times(1)).getById(ID);
    }

    @Test
    void getById_Should_Return404_When_IdNotFound() throws Exception {
        given(userService.getById(ID)).willThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/users/{id}", ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByUsername_Should_ReturnSuccess() throws Exception {
        given(userService.getByUsername(USERNAME)).willReturn(getUserDto());

        mockMvc.perform(get("/api/v1/users/username/{username}", USERNAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getUserDto())));

        then(userService).should(times(1)).getByUsername(USERNAME);
    }

    @Test
    void getByUsername_Should_Return404_When_UsernameNotFound() throws Exception {
        given(userService.getByUsername(USERNAME)).willThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/users/username/{username}", USERNAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_Should_ReturnSuccess() throws Exception {
        given(userService.create(getUserCreateRequest())).willReturn(getUserDto());

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserCreateRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(getUserDto())));

        then(userService).should(times(1)).create(getUserCreateRequest());
    }

    @Test
    void update_Should_ReturnSuccess() throws Exception {
        given(userService.update(ID, getUserUpdateRequest())).willReturn(getUserDto());

        mockMvc.perform(put("/api/v1/users/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserUpdateRequest())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getUserDto())));

        then(userService).should(times(1)).update(ID, getUserUpdateRequest());
    }

    @Test
    void updateStatus_Should_ReturnSuccess() throws Exception {
        given(userService.updateStatus(ID, USER_STATUS)).willReturn(getUserDto());

        mockMvc.perform(patch("/api/v1/users/{id}", ID)
                        .param("userStatus", USER_STATUS.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getUserDto())));

        then(userService).should(times(1)).updateStatus(ID, USER_STATUS);
    }

    @Test
    void delete_Should_ReturnSuccess() throws Exception {
        given(userService.delete(ID)).willReturn(getUserDto());

        mockMvc.perform(delete("/api/v1/users/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getUserDto())));

        then(userService).should(times(1)).delete(ID);
    }
}
