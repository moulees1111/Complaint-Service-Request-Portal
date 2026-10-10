package com.example.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.Complaint;
import com.example.entity.ComplaintStatus;
import com.example.repository.ComplaintRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComplaintSlaScheduler {

    private final ComplaintRepository complaintRepository;

    @Scheduled(fixedRate = 60000)
    public void checkComplaintSla() {

        LocalDateTime cutoff =
                LocalDateTime.now().minusHours(48);

        List<ComplaintStatus> activeStatuses = List.of(
                ComplaintStatus.OPEN,
                ComplaintStatus.ASSIGNED,
                ComplaintStatus.IN_PROGRESS
        );

        List<Complaint> overdueComplaints =
                complaintRepository
                    .findByStatusInAndCreatedAtBefore(
                        activeStatuses, cutoff
                    );

        if (overdueComplaints.isEmpty()) {
            log.info("SLA check completed. No overdue complaints.");
            return;
        }

        log.warn(
            "SLA check found {} overdue complaints",
            overdueComplaints.size()
        );

        overdueComplaints.forEach(complaint ->
            log.warn(
                "Complaint ID {} has exceeded the 48-hour SLA",
                complaint.getId()
            )
        );
    }
}