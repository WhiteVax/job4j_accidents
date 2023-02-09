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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControlTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    private final Accident accident = new Accident(1, "Accident", "txt", "Address",
            LocalDateTime.now(), new AccidentType(), new HashSet<>());

    @Test
    @WithMockUser
    void whenGetViewFindAll() throws Exception {
        this.mockMvc.perform(get("/accidents/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/index"));
    }

    @Test
    @WithMockUser
    void whenSaveAccident() throws Exception {
        var ids = new String[]{"1"};
        var idType = 1;
        this.mockMvc.perform(multipart("/accidents/save")
                        .param("name", accident.getName())
                        .param("description", accident.getDescription())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(idType))
                        .param("rIds", ids))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).create(argument.capture(), eq(ids), eq(idType));
        assertThat(argument.getValue().getName()).isEqualTo(accident.getName());
    }

    @Test
    @WithMockUser
    void whenGetFormUpdate() throws Exception {
        var ids = new String[]{"1"};
        accidentService.create(accident, ids, 1);
        when(accidentService.findById(1)).thenReturn(Optional.of(accident));
        this.mockMvc.perform(get("/accidents/formUpdate")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/editAccident"));
        verify(accidentService).findById(1);
    }

    @Test
    @WithMockUser
    void whenUpdateAccident() throws Exception {
        var ids = new String[]{"1"};
        var idType = 1;
        when(accidentService.update(accident, ids, 1)).thenReturn(true);
        this.mockMvc.perform(multipart("/accidents/update")
                        .param("id", String.valueOf(1))
                        .param("name", "update")
                        .param("description", accident.getDescription())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(idType))
                        .param("rIds", ids))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        ArgumentCaptor<Accident> arguments = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(arguments.capture(), eq(ids), eq(idType));
        assertThat(arguments.getValue().getName()).isEqualTo("update");
    }
}