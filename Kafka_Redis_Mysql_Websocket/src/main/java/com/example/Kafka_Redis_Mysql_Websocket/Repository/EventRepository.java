package com.example.Kafka_Redis_Mysql_Websocket.Repository;

import com.example.Kafka_Redis_Mysql_Websocket.Entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
