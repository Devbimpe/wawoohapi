package com.longbridge.repository;

import com.longbridge.models.StatusMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusMessageRepository extends JpaRepository<StatusMessage, Long> {
}
