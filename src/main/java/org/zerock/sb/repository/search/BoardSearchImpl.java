package org.zerock.sb.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.QBoard;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{


    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(char[] typeArr, String keyword, Pageable pageable) {
        log.info("----------search");
        QBoard board = QBoard.board;

        JPQLQuery<Board> jpqlQuery = from(board);

        //검색 조건이 있다면
        if (typeArr != null && typeArr.length > 0){

            BooleanBuilder condition = new BooleanBuilder();

            for(char type : typeArr) {
                if (type == 'T') {
                    jpqlQuery.where(board.title.contains(keyword));
                }else if (type == 'C'){
                    jpqlQuery.where(board.content.contains(keyword));
                }else if (type == 'W'){
                    jpqlQuery.where(board.writer.contains(keyword));
                }
            }
            jpqlQuery.where(condition);
        }


//        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
//        String keyword = "10";
//        char[]  typeArr = {'T','C','W'};



        jpqlQuery.where(board.bno.gt(0L)); // bno > 0

        JPQLQuery<Board> pagingQuery =
                this.getQuerydsl().applyPagination(pageable, jpqlQuery);

        List<Board> boardList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();

        return new PageImpl<>(boardList, pageable, totalCount);

    }

}
