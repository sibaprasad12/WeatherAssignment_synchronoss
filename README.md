# WeatherAssignment_synchronoss 

>> Please go through the screenshots and gif of the app flow at the bottom of the page.

## [Download APK](https://github.com/sibaprasad12/WeatherAssignment_synchronoss/blob/master/screenshots/WeatherApp_synchronoss.apk)

## Assignment Objectives
- Create a simple App that displays the current weather forecast for your devices current location, and display location and all relevant information returned in the UI.
- Persist the response so that it can be retrieved again without having to make a further network request.
- Schedule a request for every 2 hours to update and persist the latest weather response.  There should not be more than 1 request in a 2-hour period.  All background requests should only be made if Wi-Fi is connected.
- Send a HTTP request to the OpenWeatherMap API to retrieve the current local weather forecast, and parse the response.
- Produce clean and well formatted/documented code.
- Include a 1-page document that should include all design decisions made, description of the App, and any thoughts on what you would do for a future release of the App.

## Feature Implemented
- MVVM architecture with Coroutines
- Retrofit for Network call
- Room Database
- WorkManager
- Navigation Library
- Picaso for Image loading
- Dagger Hilt for dependency Injection
- DIFF Utils to ease loading
- Unit test cases
- UI test cases for database Implementation

## Architechure and lib used
- MVVM
- Data Binding
- Coroutines
- Clean Architecture
- Dagger Hilt for Dependency Injection
- Room Database For persistency

## Unit Testing And Instrumentation Testing
- Written Database test cases
- Written View Model test cases
- Written Few UI test cases


## How to Run the application
- Download the APK link given above or download the project and open in Android Studio and Sync and click on run on any Emulator or Device
- Click on the **Weather Assignment** in device dashboard
- It will fetch the location and get the weather details from the openweathermap api for current location
- On siccessful response, it will show the weather details for the current location
- On every 2 hours it will fetch the weather details for the last location
- User can add a location to fafourite weather List
- User can see the weather details list in Saved weather Section
- **Map** User can select a location and it will show the weather details for that location


## Concept Used and reason behind it
- Work Manager - To Update the weather Location in every 2 hour(Wether app is killed or not).
- The last weather locatin will be fetched in background.
- **Google Map** to select a location to see the weather details
- Used Coroutines for Background call

## What I will do for future release and Missing Part 
- Create Base module for all the common code(BaseFragment, BaseActivity, BaseInterface...)
- Creat Network Module Separately.
- Add Progurad Rule
- Add Gradle Flavour
- Optimize code and gradle speed by adding gradle script
- Kotlin Lint, Android studio Lint rule for each commit
- Add crashlytics
- More test case coverage

## Screenshots of the Weather Application
<table>
<tr>
<td>
  <img src="https://github.com/sibaprasad12/WeatherAssignment_synchronoss/blob/master/screenshots/screen1.png" width="170" height="300" />
 </td>
<td>
 <img src="https://github.com/sibaprasad12/WeatherAssignment_synchronoss/blob/master/screenshots/screen2.png" width="170" height="300"/> 
</td>
  <td>
  <img src="https://github.com/sibaprasad12/WeatherAssignment_synchronoss/blob/master/screenshots/screen3.png" width="170" height="300" />
 </td>
   <td>
  <img src="https://github.com/sibaprasad12/WeatherAssignment_synchronoss/blob/master/screenshots/screen4.png" width="170" height="300" />
 </td>
  <td>
  <img src="https://github.com/sibaprasad12/WeatherAssignment_synchronoss/blob/master/screenshots/screen5.png" width="170" height="300" />
 </td>
</tr>
</table> 

## GIF image for Application Flow
<img src="https://github.com/sibaprasad12/WeatherAssignment_synchronoss/blob/master/screenshots/weather_appflow.gif" width="250" height="500" />
