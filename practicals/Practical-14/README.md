# Practical 14 - Part B: MiniBank Capstone Integrated Production Delivery

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v14.0`

---

## 1. Objective & Problem Overview
Practical 14 is the comprehensive capstone integrating all 14 practicals into an enterprise core banking solution. It features a BankService facade, deadlock-free inter-account transfers, thread-pool batch execution, and persistent binary storage.

---

## 2. Core OOP Principles & Java Features
- **Capstone Architecture**
- **Deadlock Prevention (Ordered Locking)**
- **ExecutorService & Thread Pools**
- **BankService Facade**
- **Automated Testing**
- **Full Integration**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-14
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: How does MiniBank prevent deadlocks during concurrent inter-account transfers?**  
*A:* By enforcing deterministic lock ordering: transfers always sort the two account monitors lexicographically by account number, acquiring the 'lower' lock first. This breaks Dijkstra's circular wait condition.

**Q: Why is ExecutorService preferred over creating new Thread objects manually?**  
*A:* Creating threads is resource-intensive. ExecutorService manages a pool of reusable worker threads, controls thread lifecycle, prevents thread exhaustion DOS, and optimizes CPU core utilization.

