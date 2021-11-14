# WeatherAssignment_synchronoss 

>> Check the screenshots and GIF image of the app at the bottom of the page

## [Download APK](https://github.com/sibaprasad12/TvShowAssigmnent/blob/main/app/apk/TvShowAssignment.apk)
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
- It will fetch the location and get the weather details from the openweathermap api
- On siccessful response, it will show the weather details for the current location
- On every 2 hours it will fetch the weather details 
- There are 2 tabs Top TvShows and Favourite TvShows
- Click on the TvSHow to see the details screen
- Click Add to Favourite or remove 
- Scroll doan the list to load more items
- Click on the filter FAB icon at the bottom right
- It will display filtered TvShows by Popularity, vote average, language
- Click on the filter FAB icon at the bottom left
- It will display Sort TvShows by Tvshow Name, Popularity, Air date, Vote average, vote count


## Here is the screen shot and Gif image for the application
<table>
<tr>
<td>
  <img src="https://github.com/sibaprasad12/TvShowAssigmnent/blob/main/app/images/ss1.png" width="150" height="270" />
 </td>
<td>
 <img src="https://github.com/sibaprasad12/TvShowAssigmnent/blob/main/app/images/ss2.png" width="150" height="270"/> 
</td>
  <td>
  <img src="https://github.com/sibaprasad12/TvShowAssigmnent/blob/main/app/images/ss3.png" width="150" height="270" />
 </td>
   <td>
  <img src="https://github.com/sibaprasad12/TvShowAssigmnent/blob/main/app/images/ss6.png" width="150" height="270" />
 </td>
  <td>
  <img src="https://github.com/sibaprasad12/TvShowAssigmnent/blob/main/app/images/ss4.png" width="150" height="270" />
 </td>
<td>
 <img src="https://github.com/sibaprasad12/TvShowAssigmnent/blob/main/app/images/ss5.png" width="150" height="270"/> 
</td>
</tr>
</table> 

## GIF image
<img src="https://github.com/sibaprasad12/TvShowAssigmnent/blob/main/app/images/movieAssignment.gif" width="250" height="500" />
