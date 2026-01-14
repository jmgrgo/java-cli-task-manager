# Java TaskApp CLI

This repository contains a **CLI-based** implementation of **TaskApp** using only the standard features of Java.

## Purpose

TaskApp CLI is a **learning project** focused on understanding the core language without frameworks.

The same task management domain is implemented in multiple languages to compare:
- Language ergonomics
- Standard library capabilities
- Program structure and organization
- CLI input/output handling

This project is **not intended for production use**.

## Application Overview

TaskApp CLI is a local task manager executed from the command line.

### User Flow

1. User starts the application
2. User creates an account or logs in
3. User creates tags
4. User creates and manages tasks
5. User lists tasks and tags

## Domain Model (Subject to Change)

### User
- Identifier
- Credentials (stored locally)

### Task
- Title
- Completed status
- Overdue date
- Associated tag

### Tag
- Name

## Architecture

- Single-process application
- No frameworks
- Local persistence (file-based or in-memory)

## Technology Stack

- Java
- Standard library only

## Related Repositories

Other TaskApp CLI implementations:
- Pending

## Notes

The CLI implementation intentionally avoids external libraries to emphasize the core language.
