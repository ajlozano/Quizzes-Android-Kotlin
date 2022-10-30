package uoc.quizz

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.accessibility.AccessibilityChecks.enable
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import uoc.quizz.main.MainActivity
import java.lang.Compiler.enable


public class AccessibilityTest {
    @Test
    fun accessibilityChecks() {
        AccessibilityChecks.enable()
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            Espresso.onView(withId(R.id.button)).perform(ViewActions.click()) }
    }
}