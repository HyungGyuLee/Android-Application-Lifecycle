# Android-Application-State

- Receive active/inactive state events of the application.
- Events are forwarded to the top-level activity where the AppStateListener is declared.



## Usage

in application class

```kotlin
class ExApplication: Application() {
    private val activityLifecycle = ApplicationActivityLifecycle()
    
    override fun onCreate() {
        super.onCreate()

        // register callback
        registerActivityLifecycleCallbacks(activityLifecycle)
        activityLifecycle.applicationStateForeground {
            // enter foreground
        }

        activityLifecycle.applicationStateBackground {
            // enter background
        }
    }   

```


in Activity class

```kotlin
class ThirdActivity : AppCompatActivity(), AppStateListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        // add AppStateListener automatically when move to top activity
    }

    override fun enterForeground() {
        Log.d("debug", "ThirdActivity enterForeground")
    }

    override fun enterBackground() {
        Log.d("debug", "ThirdActivity enterBackground")
    }
}
```


## ref
https://medium.com/@iamsadesh/android-how-to-detect-when-app-goes-background-foreground-fd5a4d331f8a
