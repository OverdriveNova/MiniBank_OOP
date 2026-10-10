# Practical 10 - Part B: MiniBank Concurrent Transaction Processor Engine

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v10.0`

---

## 1. Objective & Problem Overview
Practical 10 builds a concurrent TransactionProcessor engine. It executes batch transaction workloads across multiple worker threads and validates that all financial updates reconcile with zero balance discrepancies.

---

## 2. Core OOP Principles & Java Features
- **Concurrent Workloads**
- **Thread Coordination**
- **Audit Reconciliation**
- **Consistency Guarantees**
- **Batch Processing**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-10
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: How do you verify thread safety in batch transactions?**  
*A:* By calculating the mathematical invariant: Initial Balance + Sum(Deposits) - Sum(Withdrawals) == Final Balance after all worker threads complete their execution.

**Q: What is the cost of excessive synchronization?**  
*A:* Excessive synchronization causes thread contention, where threads spend significant time waiting for locks, leading to reduced throughput and latency spikes.

