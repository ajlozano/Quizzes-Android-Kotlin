package uoc.quizz

import android.widget.Toast
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import junit.extensions.TestSetup
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uoc.quizz.main.MainActivity
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
@LargeTest
class SelectResponsesBehaviorTest {

    //private lateinit var activityScenario: ActivityScenario<MainActivity>
    @get:Rule var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun TestSetup(){
        //ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun SelectIncorrectOptionOnQuestion() {
        //Check send response without option selected
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.string.no_option_selected)).check(ViewAssertions.matches(withText(NO_OPTION_SELECTED)))

        //Check response selecting incorrect response
        onView(withId(R.id.optionOne)).perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.string.wrong_answer)).check(ViewAssertions.matches(withText(WRONG_ANSWER)))
    }

    @Test
    fun SelectCorrectOptionOnQuestion() {
        onView(withId(R.id.optionThree)).perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.string.is_correct)).check(ViewAssertions.matches(withText(IS_CORRECT)))
    }

    @Test
    fun SelectCorrectOptionsForAllQuestions() {
        onView(withId(R.id.optionThree)).perform(click())
        onView(withId(R.id.button)).perform(click())

        onView(withId(R.id.optionOne)).perform(click())
        onView(withId(R.id.button)).perform(click())

        onView(withId(R.id.optionTwo)).perform(click())
        onView(withId(R.id.button)).perform(click())

        onView(withId(R.string.congratulations)).check(ViewAssertions.matches(withText(CONGRATULATIONS)))
    }

    companion object {
        val NO_OPTION_SELECTED = "no_option_selected"
        val WRONG_ANSWER = "WRONG ANSWER!"
        val IS_CORRECT = "IS CORRECT!"
        val CONGRATULATIONS = "congratulations!"
    }
}