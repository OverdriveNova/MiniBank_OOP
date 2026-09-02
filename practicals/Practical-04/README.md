# Practical 04 - Part B: MiniBank Regex Validation & Masked Formatting

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v4.0`

---

## 1. Objective & Problem Overview
Practical 4 incorporates input sanitization and verification using regex patterns. It utilizes StringBuilder for high-performance string concatenation when generating bank statements and audit logs while masking sensitive account credentials.

---

## 2. Core OOP Principles & Java Features
- **Regular Expressions (Pattern/Matcher)**
- **String Immutability**
- **StringBuilder Performance**
- **Data Masking**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-04
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: Why use StringBuilder rather than String concatenation in statement generation?**  
*A:* Strings in Java are immutable; repeated concatenation in loops creates O(N^2) temporary objects and triggers garbage collection overhead, while StringBuilder mutates an internal dynamic buffer in O(N) time.

**Q: Explain the regex pattern ^AC[0-9]{4}$.**  
*A:* The caret (^) asserts start of string, 'AC' requires literal prefix, '[0-9]{4}' mandates exactly four digits, and '$' asserts end of string.

