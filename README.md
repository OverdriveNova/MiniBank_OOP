# MiniBank OOP Enterprise Edition

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge&logo=checkmarx&logoColor=white)]()
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)
[![Academic Project](https://img.shields.io/badge/Course-CEUE203%20OOP-purple?style=for-the-badge)]()
[![University](https://img.shields.io/badge/CHARUSAT-DEPSTAR-red?style=for-the-badge)](https://www.charusat.ac.in)

> **A progressive, multi-threaded, persistent core banking simulation engineered across 14 academic practicals.**  
> Developed for **CEUE203: Object Oriented Programming (Semester 3)** at **Devang Patel Institute of Advance Technology and Research (DEPSTAR)**, Charotar University of Science and Technology (CHARUSAT).

---

## 👨‍💻 Student Profile
- **Name:** RUDRA MODI
- **Enrollment ID:** `25DCS058`
- **GitHub:** [@OverdriveNova](https://github.com/OverdriveNova)
- **Email:** `rudramodi5525@gmail.com`
- **Department:** Computer Science & Engineering
- **Institute:** DEPSTAR, CHARUSAT

---

## 🏛️ Executive Summary & Vision

**MiniBank** demonstrates the progressive evolution of a software system from basic control flow constructs to a hardened enterprise architecture. Throughout the 14 practicals, the codebase systematically integrates:
- Modern JDK 17+ records, enums, and switch expressions.
- Strict encapsulation, invariant validation, and deep-copy cloning.
- Polymorphic inheritance hierarchies and interface-driven design.
- Runtime metadata introspection via custom annotations and Java Reflection.
- Custom checked exception hierarchies and transaction session safeguards.
- Thread-safe concurrency, intrinsic monitor synchronization, and thread pools (`ExecutorService`).
- Binary object persistence, NIO audit trails, and generic DAO repository patterns.
- Deadlock-free two-phase transaction locking protocols.

---

## 🚀 Progressive Practical Roadmap (Milestones v1.0 — v14.0)

| Practical | Milestone Tag | Title & Focus | Key Modern Java & OOP Concepts | Documentation |
| :---: | :---: | :--- | :--- | :---: |
| **01** | [`v1.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v1.0) | **MiniBank Console Menu Shell** | Java Records (`record BankInfo`), Enums, Arrow-Syntax Switch, `Scanner` input validation | [Doc 1](docs/MiniBank_Practical01.docx) |
| **02** | [`v2.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v2.0) | **Account Generator & Invariant Protection** | Private encapsulation, getters/mutators, static sequential ID counter, immutable `final` fields | [Doc 2](docs/MiniBank_Practical02.docx) |
| **03** | [`v3.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v3.0) | **Deep-Copy Cloning & Address Subsystem** | Standard Object overrides (`toString`, `equals`, `hashCode`), nested `Address`, deep-copy `Cloneable` | [Doc 3](docs/MiniBank_Practical03.docx) |
| **04** | [`v4.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v4.0) | **Regex Validation & Masked Formatting** | `java.util.regex` (`Pattern`, `Matcher`), `StringBuilder` statement assembly, sensitive data masking | [Doc 4](docs/MiniBank_Practical04.docx) |
| **05** | [`v5.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v5.0) | **Polymorphic Account Hierarchy** | Abstract base class `Account`, concrete subclasses `SavingsAccount`, `CurrentAccount`, `FixedDepositAccount` | [Doc 5](docs/MiniBank_Practical05.docx) |
| **06** | [`v6.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v6.0) | **Transaction Processing & Contracts** | Interface contracts `Transactable`, `InterestBearing`, dynamic `WithdrawRule` strategy pattern | [Doc 6](docs/MiniBank_Practical06.docx) |
| **07** | [`v7.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v7.0) | **Runtime Metadata & Annotation Validator** | Custom runtime annotations (`@Id`, `@Positive`, `@MaxLength`), Java Reflection field inspection | [Doc 7](docs/MiniBank_Practical07.docx) |
| **08** | [`v8.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v8.0) | **Robust Exception Handling Architecture** | Custom checked hierarchy (`BankException`, `InsufficientFundsException`), `BankSession` transactions | [Doc 8](docs/MiniBank_Practical08.docx) |
| **09** | [`v9.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v9.0) | **Multithreaded Account Synchronization** | `Runnable` worker threads, race condition prevention, synchronized monitor locking | [Doc 9](docs/MiniBank_Practical09.docx) |
| **10** | [`v10.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v10.0) | **Concurrent Transaction Processor Engine** | High-volume concurrent batch workloads, thread coordination, balance audit reconciliation | [Doc 10](docs/MiniBank_Practical10.docx) |
| **11** | [`v11.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v11.0) | **Persistence, Audit Log & NIO Report** | Object Serialization (`Serializable`, `ObjectOutputStream`), state persistence, Java NIO audit logs | [Doc 11](docs/MiniBank_Practical11.docx) |
| **12** | [`v12.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v12.0) | **Collections Storage & Concurrent Map** | Java Collections Framework, `ConcurrentHashMap<String, Account>`, `Comparable` and `Comparator` sorting | [Doc 12](docs/MiniBank_Practical12.docx) |
| **13** | [`v13.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v13.0) | **Generic Repository with Reflection @Id** | Generic DAO pattern (`Repository<T>`), reflection-driven entity primary key resolution | [Doc 13](docs/MiniBank_Practical13.docx) |
| **14** | [`v14.0`](https://github.com/OverdriveNova/MiniBank_OOP/tree/v14.0) | **Capstone Integrated Production Delivery** | `BankService` domain facade, deadlock-free ordered locking, `ExecutorService` thread pool, binary storage | [Doc 14](docs/MiniBank_Practical14.docx) |

---

## 🏗️ Capstone Architecture Blueprint

```
+---------------------------------------------------------------------------------+
|                                 CLIENT LAYER                                    |
|   Console UI / Batch Test Harness / Multi-Client Concurrent Worker Threads      |
+---------------------------------------------------------------------------------+
                                       |
                                       v
+---------------------------------------------------------------------------------+
|                        DOMAIN SERVICE LAYER (BankService)                       |
|   - Account Enrollment & ID Counter (@Id Generator)                             |
|   - Atomic Deposits & Withdrawals with Invariant Guarding                       |
|   - Deadlock-Free Inter-Account Transfers (Deterministic Lexicographical Order) |
|   - Automated Capstone Verification Test Suite                                  |
+---------------------------------------------------------------------------------+
           |                                                       |
           v                                                       v
+--------------------------------------+     +------------------------------------+
|         CONCURRENCY ENGINE           |     |     DOMAIN EXCEPTION HIERARCHY     |
|   - ExecutorService Thread Pool      |     |   - BankException (Checked Base)   |
|   - Reusable Worker Threads          |     |     ├── InsufficientFundsException |
|   - Intrinsic Monitor Synchronization|     |     └── AccountNotFoundException   |
+--------------------------------------+     +------------------------------------+
           |                                                       |
           +---------------------------+---------------------------+
                                       |
                                       v
+---------------------------------------------------------------------------------+
|                     DATA ACCESS LAYER (Repository<T>)                           |
|   - Generic CRUD Abstraction: save(), findById(), findAll(), delete()          |
|   - Reflection-driven Primary Key Discovery (@Id annotation scanning)           |
|   - In-Memory Fast Lookups via ConcurrentHashMap<String, T>                     |
+---------------------------------------------------------------------------------+
                                       |
                                       v
+---------------------------------------------------------------------------------+
|                      PERSISTENCE & SERIALIZATION ENGINE                         |
|   - Binary State File: data/minibank_system.dat                                 |
|   - ObjectOutputStream & ObjectInputStream Serialization                       |
|   - Java NIO Automated Directory & Storage Verification                         |
+---------------------------------------------------------------------------------+
```

---

## 🔒 Concurrency & Deadlock Prevention Mechanism

In Practical 14, inter-account transfers require simultaneous locking of two distinct `Account` objects. An undisciplined locking protocol would cause classic circular wait deadlocks:
- **Thread 1:** Transfers `A -> B` (acquires lock `A`, requests lock `B`).
- **Thread 2:** Transfers `B -> A` (acquires lock `B`, requests lock `A`).
- **Result:** System deadlocks indefinitely!

### MiniBank's Solution: Deterministic Lock Ordering
MiniBank guarantees deadlock prevention by acquiring locks in a globally deterministic order based on account identifiers:

```java
// Deterministic lock acquisition order eliminates circular wait
Account first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
Account second = (first == from) ? to : from;

synchronized (first) {
    synchronized (second) {
        from.withdraw(amount);
        to.deposit(amount);
    }
}
```

---

## 📁 Repository Structure

```
MiniBank_OOP/
├── .gitignore                          # Git exclusions for .class, .dat, and IDE files
├── README.md                           # Master project documentation (this file)
├── src/                                # Main active source code (Latest Capstone v14.0)
│   └── PartB.java                      # Full integrated banking application
├── practicals/                         # All 14 progressive practical versions preserved
│   ├── Practical-01/
│   │   ├── PartB.java                  # Practical 1: Menu shell, records, enums
│   │   └── README.md                   # Practical 1 guide & viva checklist
│   ├── Practical-02/
│   │   ├── PartB.java                  # Practical 2: Encapsulation & ID generator
│   │   └── README.md
│   ├── ...
│   └── Practical-14/
│       ├── PartB.java                  # Practical 14: Capstone integrated delivery
│       └── README.md
└── docs/                               # All 14 formal academic laboratory reports (.docx)
    ├── README.md                       # Documentation catalog & syllabus guide
    ├── MiniBank_Practical01.docx
    ├── MiniBank_Practical02.docx
    ├── ...
    └── MiniBank_Practical14.docx
```

---

## ⚡ Quickstart & Execution Guide

### Prerequisites
- Java Development Kit (JDK 17 or higher)
- Terminal / PowerShell / Bash

### 1. Run the Latest Production Capstone (Practical 14)
```bash
# Clone the repository
git clone https://github.com/OverdriveNova/MiniBank_OOP.git
cd MiniBank_OOP

# Compile and execute the master application
javac src/PartB.java
java -cp src PartB
```

### 2. Run Any Historical Version Directly
Each practical is fully preserved in its own directory:
```bash
# Example: Run Practical 7 (Custom Annotations & Reflection Validator)
cd practicals/Practical-07
javac PartB.java
java PartB
```

---

## 📑 Complete Lab Documentation Hub

All 14 academic laboratory reports submitted for Semester 3 evaluation are stored in the [`docs/`](docs/) directory in Microsoft Word (`.docx`) format. Each document contains:
- Course & Student Metadata
- Problem Aim & Technical Rationale
- Mathematical Invariants & Step-by-Step Algorithms
- Formatted, Annotated Java Source Code
- Verifiable Console Execution Outputs
- Comprehensive Viva Questions and Academic Answers

Browse the full index in [docs/README.md](docs/README.md).

---

## 🎓 Oral Examination & Viva Voce Guide

### 1. Why must `equals()` and `hashCode()` be overridden together?
> **Answer:** Java enforces a contract: equal objects (`a.equals(b) == true`) must return the same integer hash code (`a.hashCode() == b.hashCode()`). Overriding `equals` without `hashCode` causes hash-based collections (`HashSet`, `HashMap`) to place equal objects into different internal buckets, causing lookups and duplicate detection to fail silently.

### 2. How does MiniBank guarantee thread safety during batch deposits?
> **Answer:** Deposits are guarded by `synchronized` methods or blocks synchronized on the `Account` instance monitor. This guarantees atomicity and memory visibility, preventing multiple worker threads from experiencing lost update anomalies.

### 3. What is the difference between shallow copy and deep copy in cloning?
> **Answer:** A shallow copy duplicates primitive fields but copies only object references for reference fields, causing the original and clone to share mutable sub-objects. A deep copy recursively duplicates all nested objects (such as `Customer` copying `Address`), isolating internal state entirely.

### 4. What is the Generic Repository pattern and why use it?
> **Answer:** `Repository<T>` encapsulates data persistence operations behind a uniform type-safe interface (`save`, `findById`, `findAll`, `delete`), decoupling the core domain services from the underlying in-memory or binary file storage mechanisms.

---

## ⚖️ License
This project is developed for educational and academic submission purposes under the **MIT License**.

Copyright (c) 2026 Rudra Modi. All rights reserved.
