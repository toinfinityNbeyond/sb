package org.zerock.sb.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"diary" , "member"})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long fno;

    @ManyToOne
    private Diary diary;
    @ManyToOne
    private Member member;

    private int score;

    @CreationTimestamp
    private LocalDateTime regDate;

}
