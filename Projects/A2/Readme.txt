A2 Readme

Question: What happens when you rotate the screen?

The app is essentially recreated, and all of the data that was created by the app (not including widget data) is destroyed. Since the app is changing state (from onCreate to onPause to onStop to onResume), the variables that are generated in each will be erased. It is very unfortunate.
