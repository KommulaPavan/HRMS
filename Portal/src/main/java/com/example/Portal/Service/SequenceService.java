package com.example.Portal.Service;

import com.example.Portal.Entity.SequenceCounter;
import com.example.Portal.Repository.SequenceCounterRepository;
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

    /**
     * Preview the next id WITHOUT committing.
     * Reads current counter (no lock) and returns counter+1.
     */
    public String preview() {
        String dk = todayKey();
        int next = seqRepo.findById(dk).map(SequenceCounter::getCounter).orElse(0) + 1;
        return formatId(dk, next);
    }

    /**
     * Atomically increments today's counter and returns the assigned id.
     * Uses pessimistic write lock to avoid races.
     */
    @Transactional
    public String assignNextId() {
        String dk = todayKey();

        // Lock row for today; if missing, create it with counter 0 first
        SequenceCounter row = seqRepo.lockByDateKey(dk).orElse(null);
        if (row == null) {
            // create baseline row, then lock again to be safe
            row = new SequenceCounter(dk, 0);
            seqRepo.saveAndFlush(row);
            row = seqRepo.lockByDateKey(dk).orElseThrow(); // now exists
        }

        row.setCounter(row.getCounter() + 1);
        seqRepo.save(row);

        return formatId(dk, row.getCounter());
    }
}
