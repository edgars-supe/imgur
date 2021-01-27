# Simple Imgur App

This is a very simple Imgur app that pulls in the first page of Imgur's "hot" gallery and displays the list of images and galleries it returns. From there, you can view the images and albums, with some basic info.

## Architecture

The View portion uses an MVVM architecture, using Fragments and Jetpack's ViewModels. It's a single-activity app. All communication is done with RxJava (network calls, repo to ViewModel, View observes state from ViewModel, etc.). Json is parsed using `kotlinx.serialization`. There are also a couple of basic unit tests with JUnit.

## Structure

The app is divided into these packages:
* `data` - contains the Imgur repo and Retrofit service, and models used for parsing the Json.
* `di` - Dagger-related classes
* `model` - basic models used returned by the repo and used by VM
* `network` - contains the Authenticator used by OkHttp/Retrofit
* `ui` - contains the three screens of the app - `master`, `album` and `image`
* `utils` - various utilities and extensions
* `view` - custom views

## Known issues

GIFs don't load properly (still frame) or at all. Glide is supposed to be able to animate GIFs, but maybe it's a matter of how Imgur returns the image files.

The UX for single-image albums could be better - doesn't make too much sense to show an intermediary view if the album contains just one image, but you'd miss out on the basic details otherwise.

When going back to the main view, an error briefly flashes on screen.

## Building

Since the Imgur API requires registering an app, the file containing the Client ID has not been committed to git. To get the file, contact me. If you want to just look at it, [here's a video](https://drive.google.com/file/d/12ZqtbXZi8Or36_brrUb2sZRhQXro-tOc/view?usp=sharing).
