# Apples to Apples – Software Engineering Refactor Project

This repository contains a refactored version of an object-oriented card game developed as part of the **Software Engineering** course in the *Master’s programme in Industrial Computer Systems* at **Luleå University of Technology (LTU)**.

The goal of the project was to take an existing, poorly structured codebase and refactor it to meet modern software engineering standards while maintaining full functionality.

---

## Project Overview

The project implements a digital version of the **Apples to Apples** party game using Java.  
The system supports both **local** and **networked** multiplayer gameplay, with modular components for players, cards, game flow, and user interfaces.

---

## Key Features

- **Refactored architecture** following SOLID design principles  
- **Improved class structure** using Booch’s object-oriented metrics  
- Clear separation of concerns between:
  - `game` – core game logic and round flow  
  - `player` – human, bot, and remote player handling  
  - `ui` – console and network user interfaces  
  - `network` – server/client communication  
  - `phase` – structured game phases (Draw, Play, Judge, Replenish)  
- Added **JavaDoc documentation** and **JUnit test coverage**  
- Supports both **local** and **network play** modes

---

## Technical Concepts

- Object-oriented design & refactoring  
- Interface-driven architecture  
- Low coupling / high cohesion principles  
- Code quality analysis using **Booch’s metrics**  
- Unit testing and regression testing with **JUnit 4**

---

## Technologies

| Tool / Framework | Purpose |
|------------------|----------|
| Java (17+) | Core language |
| Eclipse IDE | Development environment |
| JUnit | Testing framework |
| JavaDoc | Code documentation |
| Socket API | Network communication |

---

