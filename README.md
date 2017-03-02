# J2EE Container Like

## Features

Implements some features that are provided by a J2EE container.

It provides the following features:
- @Inject annotation to inject attibutes
- Injection of singletons (using @Singleton)
- Use of the Class Loader to find elements to inject automatically
- Injection of preferred classes (using @Preferred)
- Handle cascade injection (inject object inside object injected, etc)
- Provide a lazy injection mechanism (supports self injection)
- Handle the mechanism to log method calls (using @Log)
- Handle the mechanism of transactions: Require and Require New (using @Transactional)

## Achitecture

Folders: 

- DependencyInjector: Project sources
- présentation: Powerpoint presentation
- resources: Screenshots of statistics

## DependencyInjector

The project is separated in sub project All are maven project using pom.xml files.

Projects:
- Container: The main project. It provides the basic API of a J2EE container.
- Logging: Plugin for Container project that provide logging features.
- Transactional: Plugin for Container that provide transactional features.
- Tests: Test project that contains examples (IServices and Services) and tests relatedto implemented features.
