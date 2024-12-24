# CalendarSDK Documentation

## Overview
The `CalendarSDK` is an Android library designed to determine if a given date is a holiday using multiple external APIs. It provides options for combining results from the APIs based on various consensus modes.

### Features
- Query holiday information using two APIs:
  - [Calendarific API](https://calendarific.com/api-documentation)
  - [AbstractAPI Holidays](https://www.abstractapi.com/api/holidays-api)
- Support for consensus modes:
  - `ANY`: True if any API indicates the date is a holiday.
  - `ALL`: True only if all APIs agree the date is a holiday.
  - `CONSENSUS`: True if the majority of APIs agree the date is a holiday.
- Graceful error handling and debugging support.
- Unit tests to ensure reliability.

---

## Integration

### Prerequisites
- Obtain API keys for:
  - [Calendarific API](https://calendarific.com/api-documentation)
  - [AbstractAPI Holidays](https://www.abstractapi.com/api/holidays-api)
- Include the SDK in your Android project.

### Step-by-Step Setup
1. Add the SDK files to your project (or integrate it as a library module).
2. Replace placeholder API keys (`YOUR_CALENDARIFIC_API_KEY`, `YOUR_ABSTRACT_API_KEY`, `YOUR_COUNTRY`) with your actual keys in the `HolidaySDK` class.

### Usage Example
```java
        // Get SDK singleton and initialize the apis by setting the API_Keys
val calendarSDK = CalendarSDK.instance
calendarSDK.calendarAPIKEY = <YOUR_CALENDARIFIC_API_KEY>
calendarSDK.holidayAbstractApiKey = <YOUR_ABSTRACT_API_KEY>
calendarSDK.country = <YOUR_COUNTRY>

        val checkHolidayUseCase = calendarSDK.getCheckHolidayUseCase()
// Validate the input date, then check if it is a holiday 
        if (checkHolidayUseCase.isValidDate(yearInt, monthInt, dayInt) ) {
val isHoliday = checkHolidayUseCase.isHoliday(yearInt, monthInt, dayInt, consensus)
        }
```

### API Reference
```java
       fun isValidDate(year, month, day): Bool
       suspend fun isHoliday(year, month, day, consensus): Bool
```
**Parameters**
- `year`: Integer between 0 and 3000.
- `month`: Integer between 1 and 12.
- `day`: Integer valid for the given month/year.
- `consensus`: One of `ANY`, `ALL`, or `CONSENSUS`.

### Unit Testing
The SDK includes comprehensive unit tests covering:
- Valid and invalid date inputs.
- All consensus modes.
- Error scenarios, including API failures.
- Run the tests using your preferred test runner (e.g., JUnit).

### Error Handling
- Invalid Date: The SDK validates the date and returns true if the date is valid, otherwise return false.
- API Errors: If one or more APIs fail, the SDK attempts to return a partial result or provides an error message for debugging.

### Troubleshooting
- Problem: No result returned.
  - Solution: Check API keys and network connectivity.
## Sample Application
A sample app demonstrating the SDK's usage can be found in the `app` directory.
The app integrates the `CalendarSDK` using `Clean Architecture`[https://medium.com/simform-engineering/clean-architecture-in-android-12d61c4f5318] principles.
Build the app and install the APK to see the SDK in action.

### Areas for Improvement
Due to time constraints, certain advanced techniques were not implemented in the SDK, including:
- **Utilization of Dependency Injection**: Incorporating tools such as Dagger or Hilt to enhance scalability and manageability of dependencies.
- **Extensibility for Additional APIs**: Designing the SDK to support dynamic integration of additional APIs through dependency injection or a plugin-based architecture.
- **Standardization of Interfaces and Factories**: Establishing common interfaces and factory patterns to provide a unified approach for handling requests and responses across various APIs.
