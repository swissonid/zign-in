# Feature Description

Each time you start app you will see the register screen. You can register with your name, email and
birthday.
After registration you will be redirected to the confirmation screen. When you close the app and
open it again,
you will see the registration screen again. And the whole process repeats. The old data will be
overwritten. Be aware that the loading state on the registration screen is a fake loading state, it
is just a delay.

## Registration screen know issues

- The Birthday field is a text field, which only accepts only ISO 8601 date format. In production
  I would use a VisualTransformation to format the date while the user is typing. And usally avoid
  date picker for birthday, because it is not very user friendly.
- The error message is not very user friendly. In production I would try to give the user a hint
  what is wrong with the input, as precise as possible but be aware of security issues.

# General Comments

## Things I have been focusing on

- Clean Architecture
- Testability
- Jetpack Compose

## Things I didn't focus on

- Handling configuration changes
- A propper error handling through all layers
- Unit tests everything
    - ViewModel are missing for example
- UI testing
- KMM
- CI/CD
- Documentation

## Clean Architecture

The application is based on the Clean Architecture principles. The main idea is to separate
the application into layers, where each layer has a specific responsibility. The layers are:

- Presentation: This layer is responsible for the UI and the presentation logic.
- Domain: This layer is responsible for the business logic.
- Data: This layer is responsible for the data access.

In the current implementation I kept everything in one module, but the idea is to have for each
feature module a separate module for each layer. That means that the presentation and data layer
would be a library module and the domain layer would be a pure Kotlin module.

### Presentation - Jetpack Compose

For the UI I used Jetpack Compose. I didn't change any theme or color, because I wanted to keep
the UI as simple as possible. The UI is based on the MVVM pattern.
