package org.zerock.sb.service;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.sb.dto.DiaryDTO;
import org.zerock.sb.dto.DiaryPictureDTO;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.entity.DiaryPicture;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class DiaryServiceTests {

    @Autowired
    DiaryService diaryService;

    @Test
    public void testRegister() {

        List<String> tags = IntStream.rangeClosed(1,3).mapToObj(j -> "tag_"+j).collect(Collectors.toList());

        List<DiaryPictureDTO> pictures = IntStream.rangeClosed(1,3).mapToObj(j -> {
            DiaryPictureDTO picture = DiaryPictureDTO.builder()
                    .uuid(UUID.randomUUID().toString())
                    .savePath("2021/10/18")
                    .fileName("img"+j+".jpg")
                    .idx(j)
                    .build();
            return picture;
        }).collect(Collectors.toList());

        DiaryDTO dto = DiaryDTO.builder()
                .title("title...")
                .content("content..")
                .writer("writer...")
                .tags(tags)
                .pictures(pictures)
                .build();

        diaryService.register(dto);

    }

    @Transactional(readOnly = true)

    @Test
    public void tetRead() {

        Long dno = 2L;

        DiaryDTO dto = diaryService.read(dno);

        log.info(dto);

        log.info(dto.getPictures().size());

        dto.getPictures().forEach(diaryPictureDTO -> log.info(diaryPictureDTO));


    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO <DiaryDTO> responseDTO
                = diaryService.getList(pageRequestDTO);

        responseDTO.getDtoList().forEach(diaryDTO -> {
            log.info(diaryDTO);
            log.info(diaryDTO.getTags());
            log.info(diaryDTO.getPictures());
            log.info("---------------------------");
        });

    }

}
