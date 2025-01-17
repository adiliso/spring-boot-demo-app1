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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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
        given(userService.getAll()).willReturn(Set.of(USER_DTO));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(USER_DTO))))
                .andDo(MockMvcResultHandlers.print());

        then(userService).should(times(1)).getAll();
    }

    @Test
    void getById_Should_ReturnSuccess() throws Exception {
        given(userService.getById(ID)).willReturn(USER_DTO);

        mockMvc.perform(get("/api/v1/users/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)))
                .andDo(MockMvcResultHandlers.print());

        then(userService).should(times(1)).getById(ID);
    }

    @Test
    void getById_Should_Return404_When_IdNotFound() throws Exception {
        given(userService.getById(ID)).willThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/users/{id}", ID))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getByUsername_Should_ReturnSuccess() throws Exception {
        given(userService.getByUsername(USERNAME)).willReturn(USER_DTO);

        mockMvc.perform(get("/api/v1/users/username/{username}", USERNAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)))
                .andDo(MockMvcResultHandlers.print());

        then(userService).should(times(1)).getByUsername(USERNAME);
    }

    @Test
    void getByUsername_Should_Return404_When_UsernameNotFound() throws Exception {
        given(userService.getByUsername(USERNAME)).willThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/users/username/{username}", USERNAME))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void create_Should_ReturnSuccess() throws Exception {
        given(userService.create(USER_CREATE_REQUEST)).willReturn(USER_DTO);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREATE_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)))
                .andDo(MockMvcResultHandlers.print());

        then(userService).should(times(1)).create(USER_CREATE_REQUEST);
    }

    @Test
    void update_Should_ReturnSuccess() throws Exception {
        given(userService.update(ID, USER_UPDATE_REQUEST)).willReturn(USER_DTO);

        mockMvc.perform(put("/api/v1/users/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_UPDATE_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)))
                .andDo(MockMvcResultHandlers.print());

        then(userService).should(times(1)).update(ID, USER_UPDATE_REQUEST);
    }

    @Test
    void updateStatus_Should_ReturnSuccess() throws Exception {
        given(userService.updateStatus(ID, USER_STATUS)).willReturn(USER_DTO);

        mockMvc.perform(patch("/api/v1/users/{id}", ID)
                        .param("userStatus", USER_STATUS.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)))
                .andDo(MockMvcResultHandlers.print());

        then(userService).should(times(1)).updateStatus(ID, USER_STATUS);
    }

    @Test
    void delete_Should_ReturnSuccess() throws Exception {
        given(userService.delete(ID)).willReturn(USER_DTO);

        mockMvc.perform(delete("/api/v1/users/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)))
                .andDo(MockMvcResultHandlers.print());

        then(userService).should(times(1)).delete(ID);
    }
}
