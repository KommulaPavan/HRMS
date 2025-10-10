// com/example/Portal/Service/SequenceService.java
package com.example.Portal.Service;

import com.example.Portal.Entity.SequenceCounter;
import com.example.Portal.Repository.SequenceCounterRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SequenceService {

    private final SequenceCounterRepository seqRepo;

    public SequenceService(SequenceCounterRepository seqRepo) {
        this.seqRepo = seqRepo;
    }

    /** Format: CT-<MMDDYY><NNN>  e.g. CT-091525001 */
    private String formatId(String dateKey, int n) {
        return "CT-" + dateKey + String.format("%03d", n);
    }

    /** Today as MMddyy (e.g., 091525). */
    private String todayKey() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyy"));
    }

    /** Preview next id WITHOUT committing (no locks). */
    public String preview() {
        String dk = todayKey();
        int next = seqRepo.findByDateKey(dk).map(SequenceCounter::getCounter).orElse(0) + 1;
        return formatId(dk, next);
    }

    /**
     * Atomically increments today's counter and returns the assigned id.
     * - Locks the row (PESSIMISTIC_WRITE)
     * - If the row doesn't exist, creates it safely (handles insert races)
     * - Retries a couple of times if first-insert races occur
     */
    @Transactional
    public String assignNextId() {
        String dk = todayKey();
        final int maxRetries = 3;
        int attempt = 0;

        while (true) {
            attempt++;
            // Lock existing row if present
            SequenceCounter row = seqRepo.lockByDateKey(dk).orElse(null);
            if (row == null) {
                // First insert attempt (may race)
                try {
                    seqRepo.saveAndFlush(new SequenceCounter(dk, 0));
                } catch (DataIntegrityViolationException e) {
                    // Another thread inserted the same dk concurrently; fall through to lock it
                }
                // Now lock again; must exist now
                row = seqRepo.lockByDateKey(dk).orElseThrow();
            }

            row.setCounter(row.getCounter() + 1);
            seqRepo.save(row);
            return formatId(dk, row.getCounter());
        }
    }
}
