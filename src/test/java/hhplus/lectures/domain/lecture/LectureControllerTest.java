package hhplus.lectures.domain.lecture;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.lectures.domain.service.LectureService;
import hhplus.lectures.exception.AlreadyAppliedException;
import hhplus.lectures.exception.ExceededLectureException;
import hhplus.lectures.exception.LectureNotFoundException;
import hhplus.lectures.exception.LectureOptionNotFoundException;
import hhplus.lectures.presentation.controller.LectureController;
import hhplus.lectures.presentation.dto.LectureApplyDto;
import hhplus.lectures.presentation.dto.LectureApplyResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@WebMvcTest(LectureController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
public class LectureControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureService lectureService;

    @Test
    @DisplayName("특강 신청 성공")
    public void successLectureApply() throws Exception, LectureNotFoundException, LectureOptionNotFoundException, ExceededLectureException, AlreadyAppliedException {
        //given
        String url = "/lectures/apply";
        LectureApplyDto dto = LectureApplyDto.builder()
                .userId(1L)
                .lectureId(1L)
                .optionId(1L)
                .build();

        LectureApplyResponseDto responseDto = LectureApplyResponseDto.builder()
                .userId(1L)
                .lectureId(1L)
                .optionId(1L)
                .build();


        given(lectureService.applyLecture(any(LectureApplyDto.class))).willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(post(url)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.lectureId").value(1L))
                .andExpect(jsonPath("$.optionId").value(1L));

    }

    @Test
    @DisplayName("특강 옵션을 찾을 수 없는 경우")
    public void lectureOptionNotFound() throws Exception, LectureNotFoundException, LectureOptionNotFoundException, ExceededLectureException, AlreadyAppliedException {
        //given
        String url = "/lectures/apply";
        LectureApplyDto dto = LectureApplyDto.builder()
                .userId(1L)
                .lectureId(1L)
                .optionId(1L)
                .build();

        given(lectureService.applyLecture(any(LectureApplyDto.class))).willThrow(new LectureOptionNotFoundException());

        //when
        ResultActions actions = mockMvc.perform(post(url)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("이미 신청한 경우")
    public void alreadyApplied() throws Exception, LectureNotFoundException, LectureOptionNotFoundException, ExceededLectureException, AlreadyAppliedException {
        //given
        String url = "/lectures/apply";
        LectureApplyDto dto = LectureApplyDto.builder()
                .userId(1L)
                .lectureId(1L)
                .optionId(1L)
                .build();

        given(lectureService.applyLecture(any(LectureApplyDto.class))).willThrow(new AlreadyAppliedException());

        //when
        ResultActions actions = mockMvc.perform(post(url)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("신청 가능 인원을 초과한 경우")
    public void exceededLecture() throws Exception, LectureNotFoundException, LectureOptionNotFoundException, ExceededLectureException, AlreadyAppliedException {
        //given
        String url = "/lectures/apply";
        LectureApplyDto dto = LectureApplyDto.builder()
                .userId(1L)
                .lectureId(1L)
                .optionId(1L)
                .build();

        given(lectureService.applyLecture(any(LectureApplyDto.class))).willThrow(new ExceededLectureException());

        //when
        ResultActions actions = mockMvc.perform(post(url)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andDo(print())
                .andExpect(status().isConflict());
    }

}
