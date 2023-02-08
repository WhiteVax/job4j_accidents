package ru.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.accidents.Main;
import ru.accidents.model.Accident;
import ru.accidents.model.AccidentType;
import ru.accidents.service.AccidentService;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControlTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @Test
    @WithMockUser
    void whenGetViewFindAll() throws Exception {
        this.mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/index"));
    }

    @Test
    @WithMockUser
    void whenSaveAccident() throws Exception {
        var ids = new String[]{"1", "2"};
        var idType = 1;
        this.mockMvc.perform(multipart("/saveAccident")
                        .param("name", "ДТП __")
                        .param("description", "txt")
                        .param("address", "Курчатов 8 А")
                        .param("type.id", String.valueOf(idType))
                        .param("rIds", ids))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).create(argument.capture(), eq(ids), eq(idType));
        assertThat(argument.getValue().getName()).isEqualTo("ДТП __");
    }

    @Test
    @WithMockUser
    void whenUpdateAccident() throws Exception {
        var ids = new String[]{"1", "2"};
        var idType = 1;
        var accident = new Accident(1, "Accident", "txt", "Address", LocalDateTime.now(), new AccidentType(),
                new HashSet<>());
        accidentService.create(accident, ids, idType);
        this.mockMvc.perform(post("/updateAccident")
                        .param("id", "1")
                        .param("name", "ДТП92304")
                        .param("description", accident.getDescription())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(idType))
                        .param("rIds", ids))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argument.capture(), eq(ids), eq(idType));
        assertThat(argument.getValue().getName()).isEqualTo("ДТП92304");
    }
}