package com.example.repository;

import com.example.entity.Complaint;
import com.example.entity.ComplaintPriority;
import com.example.entity.ComplaintStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByStatus(ComplaintStatus status);

    List<Complaint> findByPriority(ComplaintPriority priority);

    List<Complaint> findByUserId(Long userId);

    List<Complaint> findByAgentId(Long agentId);

    List<Complaint> findByCategoryId(Long categoryId);

List<Complaint> findByStatusInAndCreatedAtBefore(
        Collection<ComplaintStatus> statuses,
        LocalDateTime cutoff
);

    Page<Complaint> findByStatus(
            ComplaintStatus status,
            Pageable pageable
    );
    @Query("""
    	       SELECT c
    	       FROM Complaint c
    	       WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	       OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	       """)
    	List<Complaint> searchComplaints(
    	        @Param("keyword") String keyword
    	);
    @Query("""
    	       SELECT c
    	       FROM Complaint c
    	       WHERE c.priority = :priority
    	       AND c.status = :status
    	       """)
    	List<Complaint> findByPriorityAndStatus(
    	        @Param("priority") ComplaintPriority priority,
    	        @Param("status") ComplaintStatus status
    	);
    @Query(
    	    value = """
    	            SELECT *
    	            FROM complaints
    	            WHERE status = :status
    	            ORDER BY id DESC
    	            """,
    	    nativeQuery = true
    	)
    	List<Complaint> findLatestComplaintsByStatus(
    	        @Param("status") String status
    	);
}