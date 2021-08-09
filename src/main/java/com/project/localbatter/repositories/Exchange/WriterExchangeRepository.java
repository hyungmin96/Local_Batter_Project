package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WriterExchangeRepository extends JpaRepository<WriterExchangeEntity, Long> {

    @Query("select g.writerExchangeEntity from GroupBoardEntity g where g.boardId = :boardId")
    WriterExchangeEntity findWriterExchangeEntityByboardId(Long boardId);

}
