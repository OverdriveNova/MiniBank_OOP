# Practical 12 - Part B: MiniBank Collections Storage, Sorting & Concurrent Map

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v12.0`

---

## 1. Objective & Problem Overview
Practical 12 replaces static arrays with Java Collections. It uses ConcurrentHashMap for high-performance thread-safe account lookups and implements Comparable and Comparator for natural and custom sorting of bank accounts.

---

## 2. Core OOP Principles & Java Features
- **Collections Framework**
- **ConcurrentHashMap**
- **Comparable vs Comparator**
- **O(1) Hash Lookups**
- **Sorting Algorithms**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-12
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: Why is ConcurrentHashMap superior to Collections.synchronizedMap()?**  
*A:* ConcurrentHashMap uses lock striping and segment locking, allowing concurrent reads without locking and parallel writes across different buckets, whereas synchronizedMap locks the entire map for every operation.

**Q: What is the difference between Comparable and Comparator?**  
*A:* Comparable is implemented by the domain class itself to define natural ordering via compareTo(), while Comparator is an external strategy defining multiple alternative sorting orders via compare().

