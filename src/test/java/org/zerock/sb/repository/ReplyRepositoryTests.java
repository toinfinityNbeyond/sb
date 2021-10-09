package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.Reply;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insert200(){

        IntStream.rangeClosed(1,200).forEach(i -> {

            Long bno = (long)(200 - (1 % 5)); //200,199,198,196,

            int replyCount = (i % 5); //0,1,2,3,4

            IntStream.rangeClosed(0, replyCount).forEach(j -> {

                Board board = Board.builder().bno(bno).build();

                Reply reply = Reply.builder()
                        .replyText("Reply...")
                        .replyer("replyer...")
                        .board(board)
                        .build();

                replyRepository.save(reply);

            }); //inner loop

        }); // outer loop

    }

    @Test
    public void testRead(){

        long rn = 1L;

        Reply reply = replyRepository.findById(rn).get();

        log.info(reply);

    }

    @Test
    public void testByBno(){
        Long bno = 200L;

        List<Reply> replyList
                = replyRepository.findRepliesByBoard_BnoOrderByRno(bno);

        replyList.forEach(reply -> log.info(reply));

    }



}
