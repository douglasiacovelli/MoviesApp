<p float="left">
<img src="https://user-images.githubusercontent.com/1608564/39967727-8ec972a8-5697-11e8-8f5c-18ba0590cd4d.png" width="250px">
<img src="https://user-images.githubusercontent.com/1608564/39967729-8efeff40-5697-11e8-8451-ce38a0771f56.png" width="250px">
</p>

## App Overview
This app is intented to show the list of popular tv shows from an external resource with their ratings, details and similar tv shows.

It only has 2 screens: TvShowListActivity and DetailsActivity

## Technical information
It was initially developed following some sort of MVP + viewModel pattern with Data Binding.

**Google Jetpack**

Recently Google has announced the Google Jetpack (libraries of the Architecture Components with an opinion on how to use them).
So I decided to created another branch called "viewModel", where I converted the project to LiveData + ViewModel Architecture allowing me to remove the context reference I had to keep in my Presenters. I must say Google Engineers have done an awesome job with these libs and I plan to use them on new projects.

**Adapter**

I created just one adapter for both the TvShowList index and the similar tv shows displayed for each movie. I did that because they were basically handling the same kind of object (TvShow) and I could pass the layout to be inflated as a parameter on the Adaper`s constructor.

**Considerations about unit tests**

Firstly I had written the unit tests using the MockWebServer lib, so I wouldn't have to mock my "Repositories", however I got the feedback from a friend telling me that was more of an Integration Test since I testing both the Presenter and the "Repository".

Due to this reason I rewrote the tests so they would mock the Repository with Mockito so I wouldn't depend on it and test the repository separately.

**Libs Used**
- Retrofit
- RxJava
- Mockito
- Mockito-kotlin
- Android Architecture Components (ViewModel and LiveData)
- Picasso

## API
It uses the "The Movie Database" API (https://developers.themoviedb.org/3)
